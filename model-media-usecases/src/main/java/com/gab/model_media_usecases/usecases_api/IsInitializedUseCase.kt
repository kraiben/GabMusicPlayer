package com.gab.model_media_usecases.usecases_api

import kotlinx.coroutines.flow.StateFlow

public interface IsInitializedUseCase {
    public operator fun invoke(): StateFlow<Boolean>
}