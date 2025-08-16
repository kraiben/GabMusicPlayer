package com.gab.gabsmusicplayer.ui.screens.main

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
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
import com.gab.gabsmusicplayer.ui.player.MusicService
import com.gab.gabsmusicplayer.utils.GAB_CHECK
import com.gab.gabsmusicplayer.utils.moveTrackToStartOfQuery
import com.gab.gabsmusicplayer.utils.setPlaylist
import com.gab.core_music_loading.models.TrackInfoModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

class MusicViewModel @Inject constructor(
) : ViewModel() {

    val mediaController = mutableStateOf<MediaController?>(null)
    var currentMediaItem by mutableStateOf(MediaItem.EMPTY)
    var isTrackPlaying by mutableStateOf(false)
    var currentPosition by mutableLongStateOf(0L)
    var title by mutableStateOf("")
    var artist by mutableStateOf("")
    var duration by mutableLongStateOf(1L)
    var imageUri: Uri by mutableStateOf(
        currentMediaItem.mediaMetadata.artworkUri ?: Uri.EMPTY
    )
    var artworkData: Int by mutableIntStateOf(
        currentMediaItem.mediaMetadata.artworkDataType ?: 0
    )
    var isRepeatingOne by mutableStateOf(
        mediaController.value?.repeatMode == Player.REPEAT_MODE_ONE
    )
    var isShuffleModeSet by mutableStateOf(false)

    private val queryExt = mutableListOf<TrackInfoModel>()

    fun setNextTrack(track: TrackInfoModel) {
        mediaController.value?.let { mc ->
            if (mc.currentMediaItem?.mediaId == track.id.toString()) return
            queryExt.add(0, track)
            if (mc.shuffleModeEnabled) mc.shuffleModeEnabled = false
            mc.moveTrackToStartOfQuery(track)
            GAB_CHECK("____________________")
        }
    }

    private val listener = object : Player.Listener {
        override fun onRepeatModeChanged(repeatMode: Int) {
            super.onRepeatModeChanged(repeatMode)
            isRepeatingOne = mediaController.value?.repeatMode == Player.REPEAT_MODE_ONE
        }

        override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
            GAB_CHECK("onShuffleModeEnabledChanged")
            super.onShuffleModeEnabledChanged(shuffleModeEnabled)
            if (queryExt.isEmpty()) isShuffleModeSet = shuffleModeEnabled
        }

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            super.onIsPlayingChanged(isPlaying)
            isTrackPlaying = isPlaying
        }

        override fun onPlaylistMetadataChanged(mediaMetadata: MediaMetadata) {
            super.onPlaylistMetadataChanged(mediaMetadata)
            currentMediaItem = mediaController.value?.currentMediaItem ?: MediaItem.EMPTY
            title = currentMediaItem.mediaMetadata.title.toString()
            artist = currentMediaItem.mediaMetadata.artist.toString()
            duration = currentMediaItem.mediaMetadata.durationMs ?: 0L
            artworkData = currentMediaItem.mediaMetadata.artworkDataType ?: 0
            imageUri = currentMediaItem.mediaMetadata.artworkUri ?: Uri.EMPTY
        }

        override fun onIsLoadingChanged(isLoading: Boolean) {
            super.onIsLoadingChanged(isLoading)
            currentMediaItem = mediaController.value?.currentMediaItem ?: MediaItem.EMPTY
            title = currentMediaItem.mediaMetadata.title.toString()
            artist = currentMediaItem.mediaMetadata.artist.toString()
            duration = currentMediaItem.mediaMetadata.durationMs ?: 0L
            artworkData = currentMediaItem.mediaMetadata.artworkDataType ?: 0
            imageUri = currentMediaItem.mediaMetadata.artworkUri ?: Uri.EMPTY
        }

        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            GAB_CHECK("queryExt.size ${queryExt.size}")
            GAB_CHECK("reason: $reason")
            super.onMediaItemTransition(mediaItem, reason)
            currentMediaItem = mediaController.value?.currentMediaItem ?: MediaItem.EMPTY
            title = currentMediaItem.mediaMetadata.title.toString()
            artist = currentMediaItem.mediaMetadata.artist.toString()
            duration = currentMediaItem.mediaMetadata.durationMs ?: 0L
            artworkData = currentMediaItem.mediaMetadata.artworkDataType ?: 0
            imageUri = currentMediaItem.mediaMetadata.artworkUri ?: Uri.EMPTY
            mediaController.value?.let { mc ->
                if (queryExt.isEmpty()) {
                    mc.shuffleModeEnabled = isShuffleModeSet
                } else {
                    val curTrackId = currentMediaItem.mediaId
                    queryExt.removeAll {
                        it.id.toString() == curTrackId
                    }
                }
            }
        }
    }

    fun nextTrack() {
        mediaController.value?.seekToNext()
    }

    fun previousTrack() {
        mediaController.value?.seekToPrevious()
    }

    fun playPauseChange() {
        if (isTrackPlaying) mediaController.value?.pause()
        else mediaController.value?.play()
    }

    fun onSliderChange(position: Long) = mediaController.value?.seekTo(position)
    fun shuffleStateChange() {
        if (queryExt.isNotEmpty()) {
            GAB_CHECK(12)
            isShuffleModeSet = !isShuffleModeSet
        } else {
            GAB_CHECK(13)
            mediaController.value?.shuffleModeEnabled =
                !(mediaController.value?.shuffleModeEnabled ?: return)
        }
    }

    fun isRepeatingOneStateChange() = if (isRepeatingOne) {
        mediaController.value?.repeatMode = Player.REPEAT_MODE_ALL
    } else mediaController.value?.repeatMode = Player.REPEAT_MODE_ONE

    fun selectTrack(
        tracks: List<TrackInfoModel>,
        startIndex: Int = 0,
        context: Context,
        navigateToPlayer: () -> Unit,
        isShuffled: Boolean = false,
    ) {
        if (mediaController.value == null) {
            initializePlayer(
                tracks = tracks,
                startIndex = startIndex,
                context = context,
                navigateToPlayer = navigateToPlayer,
                isShuffled = isShuffled
            )
            return
        }
        if (isShuffled) {
            mediaController.value?.setPlaylist(tracks, isShuffled = true)
            queryExt.clear()
            navigateToPlayer()
            return
        }
        if (mediaController.value?.currentMediaItem?.mediaMetadata?.title != tracks[startIndex].title) {
            queryExt.clear()
            mediaController.value?.setPlaylist(tracks, startIndex, isShuffled = isShuffleModeSet)
        }
        navigateToPlayer()
    }

    private fun initializePlayer(
        tracks: List<TrackInfoModel>,
        startIndex: Int = 0,
        context: Context,
        navigateToPlayer: () -> Unit,
        isShuffled: Boolean = false,
    ) {

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
                it.setPlaylist(tracks, startIndex, isShuffled)
                it.shuffleModeEnabled = isShuffled
                startPositionUpdates()
                navigateToPlayer()
            }
        }, ContextCompat.getMainExecutor(context))
    }

    private fun startPositionUpdates() {
        viewModelScope.launch {
            while (isActive) {
                delay(250)
                mediaController.value?.let {
                    currentPosition = it.currentPosition
                }
            }
        }
    }

    override fun onCleared() {
        GAB_CHECK("ON CLEARED CALLED ON MAIN_VIEW_MODEL")
        super.onCleared()
        mediaController.value?.removeListener(listener)
        mediaController.value?.release()
    }
}