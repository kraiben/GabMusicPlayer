package com.gab.model_media_usecases.usecases_Impl

import com.gab.core_media.api.MediaPlayerRepository
import com.gab.model_media_usecases.usecases_api.GetNewTracksQueueSettingNotifierUseCase
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

public class GetNewTracksQueueSettingNotifierUseCaseImpl
    @Inject constructor(
        private val repository: MediaPlayerRepository
    ): GetNewTracksQueueSettingNotifierUseCase {
    override fun invoke(): SharedFlow<Unit> {
        return repository.getNewTracksQueueSettingNotifier()
    }
}