package com.gab.gabsmusicplayer.domain.usecases

import com.gab.gabsmusicplayer.domain.repository.SettingsRepository
import javax.inject.Inject

class SetMinDurationInSecondsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
) {

    suspend operator fun invoke(duration: Long) {
        settingsRepository.setMinDurationInSeconds(duration)
    }

}