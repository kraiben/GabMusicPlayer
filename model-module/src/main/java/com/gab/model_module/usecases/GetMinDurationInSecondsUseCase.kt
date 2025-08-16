package com.gab.model_module.usecases

import kotlinx.coroutines.flow.StateFlow

public interface GetMinDurationInSecondsUseCase {
    public operator fun invoke(): StateFlow<Long>
}