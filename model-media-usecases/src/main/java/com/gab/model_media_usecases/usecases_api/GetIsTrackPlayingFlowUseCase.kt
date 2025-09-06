package com.gab.model_media_usecases.usecases_api

import kotlinx.coroutines.flow.StateFlow

public interface GetIsTrackPlayingFlowUseCase {
    public operator fun invoke(): StateFlow<Boolean>
}