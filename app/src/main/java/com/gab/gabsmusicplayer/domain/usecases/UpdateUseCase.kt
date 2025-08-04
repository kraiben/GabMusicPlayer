package com.gab.gabsmusicplayer.domain.usecases

import com.gab.gabsmusicplayer.domain.repository.MusicLoadingRepository
import javax.inject.Inject

class UpdateUseCase @Inject constructor(private val repository: MusicLoadingRepository) {
    suspend operator fun invoke() {
        repository.update()
    }
}