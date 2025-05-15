package com.gab.gabsmusicplayer.ui.screens.main

import android.Manifest
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.gab.gabsmusicplayer.domain.models.TrackInfoModel
import com.gab.gabsmusicplayer.ui.ViewModelFactory
import com.gab.gabsmusicplayer.ui.allTracksScreen.AllTracksScreen
import com.gab.gabsmusicplayer.ui.musicPlayerScreen.MusicPlayerScreen
import com.gab.gabsmusicplayer.ui.navigation.MusicNavGraph
import com.gab.gabsmusicplayer.ui.navigation.rememberNavigationState
import com.gab.gabsmusicplayer.ui.player.MusicService
import com.gab.gabsmusicplayer.utils.GAB_CHECK
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.delay

@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MusicMainScreen(viewModelFactory: ViewModelFactory) {

    val navigationState = rememberNavigationState()
    val context = LocalContext.current
    val mediaController = remember { mutableStateOf<MediaController?>(null) }
    var currentMediaItem by remember { mutableStateOf(mediaController.value?.currentMediaItem) }
    var isTrackPlaying by remember { mutableStateOf(mediaController.value?.isPlaying ?: false) }
    var currentPosition by remember { mutableStateOf(mediaController.value?.currentPosition ?: 0L) }
    var title by remember { mutableStateOf(currentMediaItem?.mediaMetadata?.title.toString()) }
    var artist by remember { mutableStateOf(currentMediaItem?.mediaMetadata?.artist.toString()) }
    var duration by remember { mutableStateOf(currentMediaItem?.mediaMetadata?.durationMs ?: 1L) }
    var imageUri by remember {
        mutableStateOf(
            currentMediaItem?.mediaMetadata?.artworkUri ?: Uri.EMPTY
        )
    }
    var isRepeatingOne by remember { mutableStateOf(mediaController.value?.repeatMode == Player.REPEAT_MODE_ONE) }
    var isShuffled by remember { mutableStateOf(mediaController.value?.shuffleModeEnabled ?: false) }
    LaunchedEffect(mediaController.value) {
        val controller = mediaController.value ?: return@LaunchedEffect

        controller.addListener(
            object : Player.Listener {
                override fun onRepeatModeChanged(repeatMode: Int) {
                    super.onRepeatModeChanged(repeatMode)
                    isRepeatingOne = mediaController.value?.repeatMode == Player.REPEAT_MODE_ONE
                }
                override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
                    super.onShuffleModeEnabledChanged(shuffleModeEnabled)
                    isShuffled = shuffleModeEnabled
                }
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    super.onIsPlayingChanged(isPlaying)
                    isTrackPlaying = isPlaying
                }

                override fun onPlaylistMetadataChanged(mediaMetadata: MediaMetadata) {
                    super.onPlaylistMetadataChanged(mediaMetadata)
                    currentMediaItem = mediaController.value?.currentMediaItem
                    title = currentMediaItem?.mediaMetadata?.title.toString()
                    artist = currentMediaItem?.mediaMetadata?.artist.toString()
                    duration = currentMediaItem?.mediaMetadata?.durationMs ?: 0L
                    imageUri = currentMediaItem?.mediaMetadata?.artworkUri ?: Uri.EMPTY
                }

                override fun onIsLoadingChanged(isLoading: Boolean) {
                    super.onIsLoadingChanged(isLoading)
                    currentMediaItem = mediaController.value?.currentMediaItem
                    title = currentMediaItem?.mediaMetadata?.title.toString()
                    artist = currentMediaItem?.mediaMetadata?.artist.toString()
                    duration = currentMediaItem?.mediaMetadata?.durationMs ?: 0L
                    imageUri = currentMediaItem?.mediaMetadata?.artworkUri ?: Uri.EMPTY
                }

                override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                    GAB_CHECK(mediaController.value?.currentMediaItemIndex)
                    super.onMediaItemTransition(mediaItem, reason)
                    currentMediaItem = mediaController.value?.currentMediaItem
                    title = currentMediaItem?.mediaMetadata?.title.toString()
                    artist = currentMediaItem?.mediaMetadata?.artist.toString()
                    duration = currentMediaItem?.mediaMetadata?.durationMs ?: 0L
                    imageUri = currentMediaItem?.mediaMetadata?.artworkUri ?: Uri.EMPTY
                }
            }
        )
    }
    LaunchedEffect(mediaController) {
        while (true) {
            delay(250)
            currentPosition = mediaController.value?.currentPosition ?: 0L
        }
    }
    val onItemClick: (List<TrackInfoModel>, Int) -> Unit =
        { tracks: List<TrackInfoModel>, startIndex: Int ->
            if (mediaController.value == null) {
                val intent = Intent(context, MusicService::class.java)
                ContextCompat.startForegroundService(context, intent)
                val sessionToken = SessionToken(
                    context,
                    ComponentName(context, MusicService::class.java)
                )
                val controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
                controllerFuture.addListener({
                    mediaController.value = controllerFuture.get().apply {
                        val mediaItems = tracks.map {
                            MediaItem.Builder()
                                .setUri(it.path)
                                .setMediaId(it.id.toString())
                                .setMediaMetadata(
                                    MediaMetadata.Builder()
                                        .setTitle(it.title)
                                        .setArtworkUri(it.albumArtUri)
                                        .setArtist(it.artist)
                                        .setDurationMs(it.duration)
                                        .build()
                                )
                                .build()
                        }
                        setMediaItems(mediaItems)
                        seekTo(startIndex, 0L)
                        prepare()
                        play()
                    }
                }, ContextCompat.getMainExecutor(context))
            } else {
                if (mediaController.value?.currentMediaItem?.mediaMetadata?.title == tracks[startIndex].title) {
                    navigationState.navigateToPlayer()
                }
                else {
                    mediaController.value?.apply {
                        val mediaItems = tracks.map {
                            MediaItem.Builder()
                                .setUri(it.path)
                                .setMediaId(it.id.toString())
                                .setMediaMetadata(
                                    MediaMetadata.Builder()
                                        .setTitle(it.title)
                                        .setArtworkUri(it.albumArtUri)
                                        .setArtist(it.artist)
                                        .setDurationMs(it.duration)
                                        .build()
                                )
                                .build()
                        }
                        setMediaItems(mediaItems)
                        seekTo(startIndex, 0L)
                        prepare()
                        play()
                    }
                }
            }
            navigationState.navigateToPlayer()
        }
    DisposableEffect(Unit) {
        onDispose {
            mediaController.value?.release()
        }
    }


    val readExtStoragePermissionState = rememberPermissionState(
        permission =
        if (Build.VERSION.SDK_INT >= 33) Manifest.permission.READ_MEDIA_AUDIO
        else Manifest.permission.READ_EXTERNAL_STORAGE
    )

    GetPermission(readExtStoragePermissionState)
    if (readExtStoragePermissionState.status.isGranted) {
        MusicNavGraph(
            navHostController = navigationState.navHostController,

            audioPlayerScreenContent = {
                MusicPlayerScreen(
                    isTrackPlaying = isTrackPlaying,
                    currentPosition = currentPosition,
                    title = title,
                    artist = artist,
                    duration = duration,
                    imageUri = imageUri,
                    isShuffled = isShuffled,
                    isOneRepeating = isRepeatingOne,
                    onNextButtonClick = {mediaController.value?.seekToNext()},
                    onPreviousButtonClick = {mediaController.value?.seekToPrevious()},
                    onPauseButtonClick = {mediaController.value?.pause()},
                    onPlayButtonClick = {mediaController.value?.play()},
                    onSliderChange = {mediaController.value?.seekTo(it)},
                    onShuffleButtonClick = {mediaController.value?.shuffleModeEnabled = !(mediaController.value?.shuffleModeEnabled ?: false )},
                    onRepeatModeButtonClick = {
                        if (isRepeatingOne) {
                            mediaController.value?.repeatMode = Player.REPEAT_MODE_ALL
                        } else mediaController.value?.repeatMode = Player.REPEAT_MODE_ONE
                    }
                )
            },

            allTracksScreenContent = {
                AllTracksScreen(
                    viewModelFactory,
                    onTrackClickListener = { p1, p2 -> onItemClick(p1, p2) })
            }
        )

    } else {
        Text(
            text = "Request Perms",
            fontSize = 40.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = { readExtStoragePermissionState.launchPermissionRequest() })
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GetPermission(permission: PermissionState) {

    LaunchedEffect(permission) {
        when (permission.status) {
            is PermissionStatus.Denied -> {
                permission.launchPermissionRequest()
            }

            PermissionStatus.Granted -> {}
        }
    }
}