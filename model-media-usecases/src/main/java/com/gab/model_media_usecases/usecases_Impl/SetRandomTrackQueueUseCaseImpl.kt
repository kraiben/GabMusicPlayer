package com.gab.model_media_usecases.usecases_Impl

import com.gab.core_media.api.TracksListRepository
import com.gab.model_media_usecases.usecases_api.SetRandomTrackQueueUseCase
import com.gab.music_entities_module.TrackInfoModel
import javax.inject.Inject

public class SetRandomTrackQueueUseCaseImpl @Inject constructor(
    private val repository: TracksListRepository,
) : SetRandomTrackQueueUseCase {
    override suspend operator fun invoke(
        tracks: List<TrackInfoModel>,
    ) {
        repository.setTrackQueue(tracks,
            null,
            true)
    }
}