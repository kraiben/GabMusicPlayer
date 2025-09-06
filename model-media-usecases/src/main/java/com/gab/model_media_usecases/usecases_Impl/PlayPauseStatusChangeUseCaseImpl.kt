package com.gab.model_media_usecases.usecases_Impl

import com.gab.core_media.api.MediaPlayerRepository
import com.gab.model_media_usecases.usecases_api.PlayPauseStatusChangeUseCase
import javax.inject.Inject

public class PlayPauseStatusChangeUseCaseImpl @Inject constructor(private val repository: MediaPlayerRepository) :
    PlayPauseStatusChangeUseCase {
    override suspend operator fun invoke() {
        return repository.playPauseStatusChange()
    }
}