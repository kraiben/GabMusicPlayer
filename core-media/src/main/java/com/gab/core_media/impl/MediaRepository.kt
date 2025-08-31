package com.gab.core_media.impl

import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import com.gab.core_media.GAB_CHECK
import com.gab.core_media.api.MediaPlayerRepository
import com.gab.core_media.api.TracksListRepository
import com.gab.core_media.moveTrackToStartOfQuery
import com.gab.core_media.setPlaylist
import com.gab.music_entities_module.TrackInfoModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.toString

public class MediaRepository @Inject constructor(
    private val mediaController: MediaController,
) : MediaPlayerRepository, TracksListRepository {
//    private var mediaController: MediaController? = null
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val _currentMediaItem = MutableSharedFlow<MediaItem>()
    private var currentMediaItem: StateFlow<MediaItem> = flow {
        _currentMediaItem.collect { emit(it) }
    }.stateIn(
        coroutineScope,
        SharingStarted.Lazily,
        mediaController.currentMediaItem ?: MediaItem.EMPTY
    )
    private val _isTrackPlaying = MutableSharedFlow<Boolean>()
    private var isTrackPlaying: StateFlow<Boolean> = flow {
        _isTrackPlaying.collect { emit(it) }
    }.stateIn(
        coroutineScope,
        SharingStarted.Lazily,
        false
    )
    private val _currentPosition = MutableSharedFlow<Long>()
    private var currentPosition: StateFlow<Long> = flow {
        _currentPosition.collect { emit(it) }
    }.stateIn(
        coroutineScope,
        SharingStarted.Lazily,
        0L
    )
    private val _isRepeatingOne = MutableSharedFlow<Boolean>()
    private var isRepeatingOne: StateFlow<Boolean> = flow {
        _isRepeatingOne.collect { emit(it) }
    }.stateIn(
        coroutineScope,
        SharingStarted.Lazily,
        mediaController.repeatMode == Player.REPEAT_MODE_ONE
    )
    private val _isShuffleModeSet = MutableSharedFlow<Boolean>()
    private var isShuffleModeSet: StateFlow<Boolean> = flow {
        _isShuffleModeSet.collect { emit(it) }
    }.stateIn(
        coroutineScope,
        SharingStarted.Lazily,
        false
    )

    private val currentTrackUpdateReceiver = MutableSharedFlow<MediaMetadata>(replay = 1)
    private val currentTrack = flow<TrackInfoModel> {
        currentTrackUpdateReceiver.collect {
            emit(
                TrackInfoModel(
                    id = -1,
                    title = it.title.toString(),
                    artist = it.artist.toString(),
                    duration = it.durationMs ?: 0L,
                    albumArtUriIsNullPatchId = it.artworkDataType ?: 0,
                    albumArtUri = it.artworkUri ?: Uri.EMPTY,
                    path = ""
                )
            )
        }
    }.stateIn(
        coroutineScope,
        SharingStarted.Lazily,
        TrackInfoModel.EMPTY
    )

    override fun getCurrentTrackInfoFlow(): StateFlow<TrackInfoModel> = currentTrack
    override fun isInitialized(): StateFlow<Boolean> = flow {
        currentTrack.collect { emit(it != TrackInfoModel.EMPTY) }
    }.stateIn(
        coroutineScope,
        SharingStarted.Lazily,
        false
    )

    private val queryExt = mutableListOf<TrackInfoModel>()

    public override suspend fun setNextTrack(track: TrackInfoModel) {

        if (mediaController.currentMediaItem?.mediaId == track.id.toString()) return
        queryExt.add(0, track)
        if (mediaController.shuffleModeEnabled) mediaController.shuffleModeEnabled = false
        mediaController.moveTrackToStartOfQuery(track)

    }

    private val listener = object : Player.Listener {
        override fun onRepeatModeChanged(repeatMode: Int) {
            super.onRepeatModeChanged(repeatMode)
            coroutineScope.launch {
                _isRepeatingOne.emit(mediaController.repeatMode == Player.REPEAT_MODE_ONE)
            }
        }

        override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
            super.onShuffleModeEnabledChanged(shuffleModeEnabled)
            if (queryExt.isEmpty()) {
                coroutineScope.launch {
                    _isShuffleModeSet.emit(shuffleModeEnabled)
                }
            }
        }

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            super.onIsPlayingChanged(isPlaying)
            coroutineScope.launch {
                _isTrackPlaying.emit(isPlaying)
            }
        }

        override fun onPlaylistMetadataChanged(mediaMetadata: MediaMetadata) {
            super.onPlaylistMetadataChanged(mediaMetadata)
            coroutineScope.launch {
                currentTrackUpdateReceiver.emit(mediaMetadata)
            }
        }

        override fun onIsLoadingChanged(isLoading: Boolean) {
            super.onIsLoadingChanged(isLoading)
            coroutineScope.launch {
                _currentMediaItem.emit(mediaController.currentMediaItem ?: MediaItem.EMPTY)
                currentTrackUpdateReceiver.emit(currentMediaItem.value.mediaMetadata)
            }
        }

        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            super.onMediaItemTransition(mediaItem, reason)
            coroutineScope.launch {
                _currentMediaItem.emit(mediaController.currentMediaItem ?: MediaItem.EMPTY)
                currentTrackUpdateReceiver.emit(currentMediaItem.value.mediaMetadata)
            }
            mediaController.let { mc ->
                if (queryExt.isEmpty()) {
                    mc.shuffleModeEnabled = isShuffleModeSet.value
                } else {
                    val curTrackId = currentMediaItem.value.mediaId
                    queryExt.removeAll {
                        it.id.toString() == curTrackId
                    }
                }
            }
        }
    }


    override fun getCurrentPositionFlow(): StateFlow<Long> = currentPosition
    override fun getIsRepeatingOneFlow(): StateFlow<Boolean> = isRepeatingOne

    override fun getIsShuffleModeSetFlow(): StateFlow<Boolean> = isShuffleModeSet

    override fun getIsTrackPlayingFlow(): StateFlow<Boolean> = isTrackPlaying

    override suspend fun playPauseStatusChange() {playPauseChange()}

    public override suspend fun nextTrack() {
        mediaController.seekToNext()
    }

    public override suspend fun previousTrack() {
        mediaController.seekToPrevious()
    }
    public fun playPauseChange() {
        if (isTrackPlaying.value) mediaController.pause()
        else mediaController.play()
    }

    public override suspend fun changePosition(position: Long): Unit =
        mediaController.seekTo(position)

    public override suspend fun shuffleStateChange() {
        if (queryExt.isNotEmpty()) {
            coroutineScope.launch {
                _isShuffleModeSet.emit(!isShuffleModeSet.value)
            }
        } else {
            mediaController.shuffleModeEnabled = !(mediaController.shuffleModeEnabled)
        }
    }

    public override suspend fun isRepeatingOneStateChange(): Unit = if (isRepeatingOne.value) {
        mediaController.repeatMode = Player.REPEAT_MODE_ALL
    } else mediaController.repeatMode = Player.REPEAT_MODE_ONE

    public override suspend fun setTrackQueue(
        tracks: List<TrackInfoModel>,
        startIndex: Int?,
        isShuffledModMustBeSet: Boolean,
    ) {
        if (mediaController.currentMediaItem == null) {

            return initializePlayer(
                tracks = tracks,
                startIndex = startIndex,
                isShuffled = isShuffledModMustBeSet
            )
        }
        if (isShuffledModMustBeSet) {
            mediaController.setPlaylist(tracks, isShuffled = true)
            queryExt.clear()
            return
        }
        if (mediaController.currentMediaItem?.mediaMetadata?.title != tracks[startIndex ?: 0].title) {
            queryExt.clear()
            mediaController.setPlaylist(tracks, startIndex, isShuffled = isShuffleModeSet.value)
        }
    }

    private fun initializePlayer(
        tracks: List<TrackInfoModel>,
        startIndex: Int?,
        isShuffled: Boolean,
    ) {
        with(mediaController) {
            addListener(listener)
            setPlaylist(tracks, startIndex, isShuffled)
            shuffleModeEnabled = isShuffled
        }
        startPositionUpdates()
    }

    private fun startPositionUpdates() {
        coroutineScope.launch {
            while (isActive) {
                delay(250)
                _currentPosition.emit(mediaController.currentPosition)
            }
            GAB_CHECK("startPositionUpdates no more active")
        }
    }
}