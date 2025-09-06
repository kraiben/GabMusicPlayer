package com.gab.model_media_usecases.usecases_Impl

import com.gab.core_media.api.TracksListRepository
import com.gab.model_media_usecases.usecases_api.SetTrackQueueUseCase
import com.gab.music_entities_module.TrackInfoModel
import javax.inject.Inject

public class SetTrackQueueUseCaseImpl @Inject constructor(
    private val repository: TracksListRepository,
) : SetTrackQueueUseCase {
    override suspend operator fun invoke(
        tracks: List<TrackInfoModel>,
        startIndex: Int?,
    ) {
        repository.setTrackQueue(tracks,
                startIndex,
                false)
    }
}