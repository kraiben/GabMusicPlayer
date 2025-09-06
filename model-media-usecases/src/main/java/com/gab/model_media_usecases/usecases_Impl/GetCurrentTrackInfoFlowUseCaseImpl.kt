package com.gab.model_media_usecases.usecases_Impl

import com.gab.core_media.api.MediaPlayerRepository
import com.gab.model_media_usecases.usecases_api.GetCurrentTrackInfoFlowUseCase
import com.gab.music_entities_module.TrackInfoModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

public class GetCurrentTrackInfoFlowUseCaseImpl @Inject constructor(private val repository: MediaPlayerRepository) :
    GetCurrentTrackInfoFlowUseCase {
    override operator fun invoke(): StateFlow<TrackInfoModel> {
        return repository.getCurrentTrackInfoFlow()
    }
}