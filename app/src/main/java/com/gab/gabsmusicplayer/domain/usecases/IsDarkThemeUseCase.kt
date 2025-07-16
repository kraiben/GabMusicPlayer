package com.gab.gabsmusicplayer.domain.usecases

import com.gab.gabsmusicplayer.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class IsDarkThemeUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
) {
    operator fun invoke(): StateFlow<Boolean> = settingsRepository.isThemeDark()
}