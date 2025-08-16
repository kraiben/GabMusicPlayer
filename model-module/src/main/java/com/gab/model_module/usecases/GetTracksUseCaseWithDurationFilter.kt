package com.gab.model_module.usecases

import com.gab.core_music_loading.models.TrackInfoModel
import kotlinx.coroutines.flow.Flow

public interface GetTracksUseCaseWithDurationFilter {

    public operator fun invoke(): Flow<List<TrackInfoModel>>

}