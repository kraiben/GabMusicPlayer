package com.gab.core_media.api

import com.gab.music_entities_module.TrackInfoModel
import kotlinx.coroutines.flow.StateFlow

public interface MediaPlayerRepository {
    public fun getCurrentTrackInfoFlow(): StateFlow<TrackInfoModel>
    public fun getCurrentPositionFlow(): StateFlow<Long>
    public fun getIsRepeatingOneFlow(): StateFlow<Boolean>
    public fun getIsShuffleModeSetFlow(): StateFlow<Boolean>
    public fun getIsTrackPlayingFlow(): StateFlow<Boolean>
    public fun isInitialized(): StateFlow<Boolean>

    public suspend fun nextTrack()
    public suspend fun previousTrack()
    public suspend fun playPauseStatusChange()

    public suspend fun shuffleStateChange()
    public suspend fun isRepeatingOneStateChange()
    public suspend fun changePosition(position: Long)
}