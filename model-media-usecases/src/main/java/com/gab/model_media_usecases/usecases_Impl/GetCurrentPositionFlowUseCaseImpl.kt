package com.gab.model_media_usecases.usecases_Impl

import com.gab.core_media.api.MediaPlayerRepository
import com.gab.model_media_usecases.usecases_api.GetCurrentPositionFlowUseCase
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

public class GetCurrentPositionFlowUseCaseImpl @Inject constructor(private val repository: MediaPlayerRepository) :
    GetCurrentPositionFlowUseCase {
    override operator fun invoke(): StateFlow<Long> {
        return repository.getCurrentPositionFlow()
    }
}