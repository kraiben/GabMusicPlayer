package com.gab.model_media_usecases.usecases_Impl

import com.gab.core_media.api.MediaPlayerRepository
import com.gab.model_media_usecases.usecases_api.IsRepeatingOneStateChangeUseCase
import javax.inject.Inject

public class IsRepeatingOneStateChangeUseCaseImpl @Inject constructor(private val repository: MediaPlayerRepository) :
    IsRepeatingOneStateChangeUseCase {
    override suspend operator fun invoke() {
        return repository.isRepeatingOneStateChange()
    }
}