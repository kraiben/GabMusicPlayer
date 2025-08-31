package com.gab.model_media_usecases.usecases_Impl

import com.gab.core_media.api.MediaPlayerRepository
import com.gab.model_media_usecases.usecases_api.ShuffleStateChangeUseCase
import javax.inject.Inject

public class ShuffleStateChangeUseCaseImpl @Inject constructor(private val repository: MediaPlayerRepository) :
    ShuffleStateChangeUseCase {
    override suspend operator fun invoke() {
        return repository.shuffleStateChange()
    }
}