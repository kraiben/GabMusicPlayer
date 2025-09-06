package com.gab.model_media_usecases.usecases_Impl

import com.gab.core_media.api.MediaPlayerRepository
import com.gab.model_media_usecases.usecases_api.PreviousTrackUseCase
import javax.inject.Inject

public class PreviousTrackUseCaseImpl @Inject constructor(private val repository: MediaPlayerRepository) :
    PreviousTrackUseCase {
    override suspend operator fun invoke() {
        return repository.previousTrack()
    }
}