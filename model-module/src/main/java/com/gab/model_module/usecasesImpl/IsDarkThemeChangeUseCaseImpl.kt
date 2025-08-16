package com.gab.model_module.usecasesImpl

import com.gab.core_settings.api.data.SettingsRepository
import com.gab.model_module.usecases.IsDarkThemeChangeUseCase
import javax.inject.Inject

public class IsDarkThemeChangeUseCaseImpl @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : IsDarkThemeChangeUseCase {

    override suspend operator fun invoke() {
        settingsRepository.isDarkThemeChange()
    }

}