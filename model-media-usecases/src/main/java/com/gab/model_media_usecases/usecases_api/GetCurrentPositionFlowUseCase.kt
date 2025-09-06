package com.gab.model_media_usecases.usecases_api

import kotlinx.coroutines.flow.StateFlow

public interface GetCurrentPositionFlowUseCase {
    public operator fun invoke(): StateFlow<Long>
}