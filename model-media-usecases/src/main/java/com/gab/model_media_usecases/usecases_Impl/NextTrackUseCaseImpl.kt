package com.gab.model_media_usecases.usecases_Impl

import com.gab.core_media.api.MediaPlayerRepository
import com.gab.model_media_usecases.usecases_api.NextTrackUseCase
import javax.inject.Inject

public class NextTrackUseCaseImpl @Inject constructor(private val repository: MediaPlayerRepository) :
    NextTrackUseCase {
    override suspend operator fun invoke() {
        return repository.nextTrack()
    }
}