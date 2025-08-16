package com.gab.model_module.usecasesImpl

import com.gab.core_settings.api.data.SettingsRepository
import com.gab.model_module.usecases.DecrementDurationUseCase
import javax.inject.Inject

public class DecrementDurationUseCaseImpl @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : DecrementDurationUseCase {

    override suspend operator fun invoke() {
        settingsRepository.decrementMinDuration()
    }

}