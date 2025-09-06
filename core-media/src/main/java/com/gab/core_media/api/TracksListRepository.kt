package com.gab.core_media.api

import com.gab.music_entities_module.TrackInfoModel

public interface TracksListRepository {
    public suspend fun setTrackQueue(
        tracks: List<TrackInfoModel>,
        startIndex: Int?,
        isShuffledModMustBeSet: Boolean,
    )
    public suspend fun setNextTrack(track: TrackInfoModel)
}