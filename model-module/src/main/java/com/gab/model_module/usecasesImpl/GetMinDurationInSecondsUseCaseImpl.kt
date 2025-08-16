package com.gab.model_module.usecasesImpl

import com.gab.core_settings.api.data.SettingsRepository
import com.gab.model_module.usecases.GetMinDurationInSecondsUseCase
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

public class GetMinDurationInSecondsUseCaseImpl @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : GetMinDurationInSecondsUseCase {
    override operator fun invoke(): StateFlow<Long> = settingsRepository.getMinDurationInSeconds()
}