package com.gab.model_media_usecases.usecases_Impl

import com.gab.core_media.api.MediaPlayerRepository
import com.gab.model_media_usecases.usecases_api.GetIsShuffleModeSetFlowUseCase
import com.gab.model_media_usecases.usecases_api.IsInitializedUseCase
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

public class IsInitializedUseCaseImpl @Inject constructor(private val repository: MediaPlayerRepository) :
    IsInitializedUseCase {
    override operator fun invoke(): StateFlow<Boolean> {
        return repository.isInitialized()
    }
}