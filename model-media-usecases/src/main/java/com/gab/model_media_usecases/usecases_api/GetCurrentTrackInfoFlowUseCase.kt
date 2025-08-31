package com.gab.model_media_usecases.usecases_api

import com.gab.music_entities_module.TrackInfoModel
import kotlinx.coroutines.flow.StateFlow

public interface GetCurrentTrackInfoFlowUseCase {
    public operator fun invoke(): StateFlow<TrackInfoModel>
}