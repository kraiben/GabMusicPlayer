package com.gab.gabsmusicplayer.ui.screens.main

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.gab.gabsmusicplayer.data.repository.MusicRepositoryImpl
import com.gab.gabsmusicplayer.domain.models.TrackInfoModel
import com.gab.gabsmusicplayer.ui.navigation.NavigationState
import com.gab.gabsmusicplayer.ui.player.MusicService
import com.gab.gabsmusicplayer.utils.GAB_CHECK
import com.gab.gabsmusicplayer.utils.setPlaylist
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: MusicRepositoryImpl,
) : ViewModel() {

//    val tracks = repository.getTracks()

    val mediaController = mutableStateOf<MediaController?>(null)
    var currentMediaItem by mutableStateOf(mediaController.value?.currentMediaItem)
    var isTrackPlaying by mutableStateOf(mediaController.value?.isPlaying ?: false)
    var currentPosition by mutableStateOf(mediaController.value?.currentPosition ?: 0L)
    var title by mutableStateOf(currentMediaItem?.mediaMetadata?.title.toString())
    var artist by mutableStateOf(currentMediaItem?.mediaMetadata?.artist.toString())
    var duration by mutableStateOf(currentMediaItem?.mediaMetadata?.durationMs ?: 1L)
    var imageUri by mutableStateOf(
        currentMediaItem?.mediaMetadata?.artworkUri ?: Uri.EMPTY
    )
    var isRepeatingOne by mutableStateOf(
        mediaController.value?.repeatMode == Player.REPEAT_MODE_ONE
    )
    var isShuffled by mutableStateOf(mediaController.value?.shuffleModeEnabled ?: false)

    private val listener = object : Player.Listener {
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
            super.onMediaItemTransition(mediaItem, reason)
            currentMediaItem = mediaController.value?.currentMediaItem
            title = currentMediaItem?.mediaMetadata?.title.toString()
            artist = currentMediaItem?.mediaMetadata?.artist.toString()
            duration = currentMediaItem?.mediaMetadata?.durationMs ?: 0L
            imageUri = currentMediaItem?.mediaMetadata?.artworkUri ?: Uri.EMPTY
        }
    }

    fun nextTrack() = mediaController.value?.seekToNext()
    fun previousTrack() = mediaController.value?.seekToPrevious()
    fun playPauseChange() {
        if (isTrackPlaying) mediaController.value?.pause()
        else mediaController.value?.play()
    }

    fun onSliderChange(position: Long) = mediaController.value?.seekTo(position)
    fun shuffleStateChange() {
        mediaController.value?.shuffleModeEnabled =
            !(mediaController.value?.shuffleModeEnabled ?: false)
    }

    fun isRepeatingOneStateChange() = if (isRepeatingOne) {
        mediaController.value?.repeatMode = Player.REPEAT_MODE_ALL
    } else mediaController.value?.repeatMode = Player.REPEAT_MODE_ONE

    fun selectTrack(
        tracks: List<TrackInfoModel>,
        startIndex: Int,
        context: Context,
        navigationState: NavigationState,
    ) {
        if (mediaController.value == null) {
            val intent = Intent(context, MusicService::class.java)
            ContextCompat.startForegroundService(context, intent)
            val sessionToken = SessionToken(
                context,
                ComponentName(context, MusicService::class.java)
            )
            val controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
            controllerFuture.addListener({
                mediaController.value = controllerFuture.get().also {
                    it.addListener(listener)
                    it.setPlaylist(tracks, startIndex)
                    updateStateFromController()
                    startPositionUpdates()
                    navigationState.navigateToPlayer()
                }
            }, ContextCompat.getMainExecutor(context))
            return
        }
        if (mediaController.value?.currentMediaItem?.mediaMetadata?.title != tracks[startIndex].title) {
            mediaController.value?.setPlaylist(tracks, startIndex)
        }
        navigationState.navigateToPlayer()
    }

    private fun updateStateFromController() {
        mediaController.value?.let { controller ->
            currentMediaItem = controller.currentMediaItem
            isTrackPlaying = controller.isPlaying
            currentPosition = controller.currentPosition
            title = controller.currentMediaItem?.mediaMetadata?.title.toString()
            artist = controller.currentMediaItem?.mediaMetadata?.artist.toString()
            duration = controller.currentMediaItem?.mediaMetadata?.durationMs ?: 0L
            imageUri = controller.currentMediaItem?.mediaMetadata?.artworkUri ?: Uri.EMPTY
            isRepeatingOne = controller.repeatMode == Player.REPEAT_MODE_ONE
            isShuffled = controller.shuffleModeEnabled
        }
    }

    private fun startPositionUpdates() {
        viewModelScope.launch {
            while (isActive) {  // Используем isActive вместо true
                delay(250)
                mediaController.value?.let {
                    currentPosition = it.currentPosition
                }
            }
        }
    }

    //    private fun setPlaylist(tracks: List<TrackInfoModel>, startIndex: Int) {
//        mediaController.value?.apply {
//            val mediaItems = tracks.map {
//                MediaItem.Builder()
//                    .setUri(it.path)
//                    .setMediaId(it.id.toString())
//                    .setMediaMetadata(
//                        MediaMetadata.Builder()
//                            .setTitle(it.title)
//                            .setArtworkUri(it.albumArtUri)
//                            .setArtist(it.artist)
//                            .setDurationMs(it.duration)
//                            .build()
//                    )
//                    .build()
//            }
//            setMediaItems(mediaItems)
//            seekTo(startIndex, 0L)
//            prepare()
//            play()
//        }
//    }
    override fun onCleared() {
        GAB_CHECK("ON CLEARED CALLED ON MAIN_VIEW_MODEL")
        super.onCleared()
        mediaController.value?.removeListener(listener)
        mediaController.value?.release()
    }

}