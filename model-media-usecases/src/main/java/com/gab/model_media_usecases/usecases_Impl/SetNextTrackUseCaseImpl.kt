package com.gab.model_media_usecases.usecases_Impl

import com.gab.core_media.api.TracksListRepository
import com.gab.model_media_usecases.usecases_api.SetNextTrackUseCase
import com.gab.music_entities_module.TrackInfoModel
import javax.inject.Inject

public class SetNextTrackUseCaseImpl @Inject constructor(
    private val repository: TracksListRepository,
) : SetNextTrackUseCase {
    override suspend operator fun invoke(track: TrackInfoModel) {
        repository.setNextTrack(track)
    }
}