package com.gab.model_module.usecases

import com.gab.core_music_loading.models.TrackInfoModel
import kotlinx.coroutines.flow.StateFlow

public interface GetTracksUseCaseWithoutDurationFilter {
    public operator fun invoke(): StateFlow<List<TrackInfoModel>>
}