package com.gab.gabsmusicplayer.ui.player

import androidx.lifecycle.ViewModel
import jakarta.inject.Inject

class MusicViewModel @Inject constructor(): ViewModel()
//
//import android.net.Uri
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableIntStateOf
//import androidx.compose.runtime.mutableLongStateOf
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import androidx.media3.common.MediaItem
//import androidx.media3.common.MediaMetadata
//import androidx.media3.common.Player
//import androidx.media3.session.MediaController
//import com.gab.music_entities_module.TrackInfoModel
//import com.gab.gabsmusicplayer.utils.GAB_CHECK
//import com.gab.core_media.moveTrackToStartOfQuery
//import com.gab.core_media.setPlaylist
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.isActive
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//class MusicViewModel @Inject constructor(
//    private val mediaController: MediaController,
//)
//    : ViewModel()
//{
//    var currentMediaItem by mutableStateOf(MediaItem.EMPTY)
//    var isTrackPlaying by mutableStateOf(false)
//    var currentPosition by mutableLongStateOf(0L)
//    var title by mutableStateOf("")
//    var artist by mutableStateOf("")
//    var duration by mutableLongStateOf(1L)
//    var imageUri: Uri by mutableStateOf(
//        currentMediaItem.mediaMetadata.artworkUri ?: Uri.EMPTY
//    )
//    var artworkData: Int by mutableIntStateOf(
//        currentMediaItem.mediaMetadata.artworkDataType ?: 0
//    )
//    var isRepeatingOne by mutableStateOf(
//        mediaController.repeatMode == Player.REPEAT_MODE_ONE
//    )
//    var isShuffleModeSet by mutableStateOf(false)
//
//    private val queryExt = mutableListOf<TrackInfoModel>()
//
//    fun setNextTrack(track: TrackInfoModel) {
//        mediaController.let { mc ->
//            if (mc.currentMediaItem?.mediaId == track.id.toString()) return
//            queryExt.add(0, track)
//            if (mc.shuffleModeEnabled) mc.shuffleModeEnabled = false
//            mc.moveTrackToStartOfQuery(track)
//            GAB_CHECK("____________________")
//        }
//    }
//
//    private val listener = object : Player.Listener {
//        override fun onRepeatModeChanged(repeatMode: Int) {
//            super.onRepeatModeChanged(repeatMode)
//            isRepeatingOne = mediaController.repeatMode == Player.REPEAT_MODE_ONE
//        }
//
//        override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
//            GAB_CHECK("onShuffleModeEnabledChanged")
//            super.onShuffleModeEnabledChanged(shuffleModeEnabled)
//            if (queryExt.isEmpty()) isShuffleModeSet = shuffleModeEnabled
//        }
//
//        override fun onIsPlayingChanged(isPlaying: Boolean) {
//            super.onIsPlayingChanged(isPlaying)
//            isTrackPlaying = isPlaying
//        }
//
//        override fun onPlaylistMetadataChanged(mediaMetadata: MediaMetadata) {
//            super.onPlaylistMetadataChanged(mediaMetadata)
//            currentMediaItem = mediaController.currentMediaItem ?: MediaItem.EMPTY
//            title = currentMediaItem.mediaMetadata.title.toString()
//            artist = currentMediaItem.mediaMetadata.artist.toString()
//            duration = currentMediaItem.mediaMetadata.durationMs ?: 0L
//            artworkData = currentMediaItem.mediaMetadata.artworkDataType ?: 0
//            imageUri = currentMediaItem.mediaMetadata.artworkUri ?: Uri.EMPTY
//        }
//
//        override fun onIsLoadingChanged(isLoading: Boolean) {
//            super.onIsLoadingChanged(isLoading)
//            currentMediaItem = mediaController.currentMediaItem ?: MediaItem.EMPTY
//            title = currentMediaItem.mediaMetadata.title.toString()
//            artist = currentMediaItem.mediaMetadata.artist.toString()
//            duration = currentMediaItem.mediaMetadata.durationMs ?: 0L
//            artworkData = currentMediaItem.mediaMetadata.artworkDataType ?: 0
//            imageUri = currentMediaItem.mediaMetadata.artworkUri ?: Uri.EMPTY
//        }
//
//        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
//            GAB_CHECK("queryExt.size ${queryExt.size}")
//            GAB_CHECK("reason: $reason")
//            super.onMediaItemTransition(mediaItem, reason)
//            currentMediaItem = mediaController.currentMediaItem ?: MediaItem.EMPTY
//            title = currentMediaItem.mediaMetadata.title.toString()
//            artist = currentMediaItem.mediaMetadata.artist.toString()
//            duration = currentMediaItem.mediaMetadata.durationMs ?: 0L
//            artworkData = currentMediaItem.mediaMetadata.artworkDataType ?: 0
//            imageUri = currentMediaItem.mediaMetadata.artworkUri ?: Uri.EMPTY
//            mediaController.let { mc ->
//                if (queryExt.isEmpty()) {
//                    mc.shuffleModeEnabled = isShuffleModeSet
//                } else {
//                    val curTrackId = currentMediaItem.mediaId
//                    queryExt.removeAll {
//                        it.id.toString() == curTrackId
//                    }
//                }
//            }
//        }
//    }
//
//    fun nextTrack() {
//        mediaController.seekToNext()
//    }
//
//    fun previousTrack() {
//        mediaController.seekToPrevious()
//    }
//
//    fun playPauseChange() {
//        if (isTrackPlaying) mediaController.pause()
//        else mediaController.play()
//    }
//
//    fun onSliderChange(position: Long) = mediaController.seekTo(position)
//    fun shuffleStateChange() {
//        if (queryExt.isNotEmpty()) {
//            GAB_CHECK(12)
//            isShuffleModeSet = !isShuffleModeSet
//        } else {
//            GAB_CHECK(13)
//            mediaController.shuffleModeEnabled = !(mediaController.shuffleModeEnabled)
//        }
//    }
//
//    fun isRepeatingOneStateChange() = if (isRepeatingOne) {
//        mediaController.repeatMode = Player.REPEAT_MODE_ALL
//    } else mediaController.repeatMode = Player.REPEAT_MODE_ONE
//
//    fun selectTrack(
//        tracks: List<TrackInfoModel>,
//        startIndex: Int = 0,
//        navigateToPlayer: () -> Unit,
//        isShuffled: Boolean = false,
//    ) {
//        if (mediaController.currentMediaItem == null) {
//            initializePlayer(
//                tracks = tracks,
//                startIndex = startIndex,
//                navigateToPlayer = navigateToPlayer,
//                isShuffled = isShuffled
//            )
//            return
//        }
//        if (isShuffled) {
//            mediaController.setPlaylist(tracks, isShuffled = true)
//            queryExt.clear()
//            navigateToPlayer()
//            return
//        }
//        if (mediaController.currentMediaItem?.mediaMetadata?.title != tracks[startIndex].title) {
//            queryExt.clear()
//            mediaController.setPlaylist(tracks, startIndex, isShuffled = isShuffleModeSet)
//        }
//        navigateToPlayer()
//    }
//
//    private fun initializePlayer(
//        tracks: List<TrackInfoModel>,
//        startIndex: Int = 0,
//        navigateToPlayer: () -> Unit,
//        isShuffled: Boolean = false,
//    ) {
//        with (mediaController) {
//            addListener(listener)
//            setPlaylist(tracks, startIndex, isShuffled)
//            shuffleModeEnabled = isShuffled
//        }
//        startPositionUpdates()
//        navigateToPlayer()
//
//    }
//
//    private fun startPositionUpdates() {
//        viewModelScope.launch {
//            while (isActive) {
//                delay(250)
//                mediaController.let {
//                    currentPosition = it.currentPosition
//                }
//            }
//        }
//    }
//
//    override fun onCleared() {
//        GAB_CHECK("ON CLEARED CALLED ON MAIN_VIEW_MODEL")
//        super.onCleared()
//        mediaController.removeListener(listener)
//        mediaController.release()
//    }
//}