package com.gab.gabsmusicplayer.domain.usecases

import com.gab.gabsmusicplayer.domain.repository.SettingsRepository
import javax.inject.Inject

class IncrementDurationUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
) {

    suspend operator fun invoke() {
        settingsRepository.incrementMinDuration()
    }

}