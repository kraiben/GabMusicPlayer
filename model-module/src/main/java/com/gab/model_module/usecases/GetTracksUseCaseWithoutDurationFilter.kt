package com.gab.model_module.usecases

import com.gab.music_entities_module.TrackInfoModel
import kotlinx.coroutines.flow.StateFlow

public interface GetTracksUseCaseWithoutDurationFilter {
    public operator fun invoke(): StateFlow<List<TrackInfoModel>>
}