package com.gab.model_media_usecases.usecases_api

import com.gab.music_entities_module.TrackInfoModel

public interface SetRandomTrackQueueUseCase {
    public suspend operator fun invoke(
        tracks: List<TrackInfoModel>
    )
}