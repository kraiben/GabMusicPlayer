package com.gab.model_media_usecases.usecases_Impl

import com.gab.core_media.api.MediaPlayerRepository
import com.gab.model_media_usecases.usecases_api.*
import javax.inject.Inject

public class ChangePositionUseCaseImpl @Inject constructor(private val repository: MediaPlayerRepository) :
    ChangePositionUseCase {
    override suspend operator fun invoke(position: Long) {
        return repository.changePosition(position)
    }
}