package com.gab.gabsmusicplayer.domain.usecases

import com.gab.gabsmusicplayer.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMinDurationInSecondsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
) {
    operator fun invoke(): Flow<Long> = settingsRepository.getMinDurationInSeconds()
}