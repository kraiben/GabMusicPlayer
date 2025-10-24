package com.gab.model_media_usecases.usecases_api

import kotlinx.coroutines.flow.SharedFlow

public interface GetNewTracksQueueSettingNotifierUseCase {
    public operator fun invoke(): SharedFlow<Unit>
}