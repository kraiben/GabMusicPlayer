package com.gab.model_module.usecasesImpl

import com.gab.core_settings.api.data.SettingsRepository
import com.gab.model_module.usecases.IncrementDurationUseCase
import javax.inject.Inject

public class IncrementDurationUseCaseImpl @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : IncrementDurationUseCase {

    override suspend operator fun invoke() {
        settingsRepository.incrementMinDuration()
    }

}