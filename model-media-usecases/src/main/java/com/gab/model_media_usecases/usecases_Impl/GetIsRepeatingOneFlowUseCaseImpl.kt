package com.gab.model_media_usecases.usecases_Impl

import com.gab.core_media.api.MediaPlayerRepository
import com.gab.model_media_usecases.usecases_api.GetIsRepeatingOneFlowUseCase
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

public class GetIsRepeatingOneFlowUseCaseImpl @Inject constructor(private val repository: MediaPlayerRepository) :
    GetIsRepeatingOneFlowUseCase {
    override operator fun invoke(): StateFlow<Boolean> {
        return repository.getIsRepeatingOneFlow()
    }
}