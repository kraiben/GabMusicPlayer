package com.gab.model_module.usecases

import com.gab.music_entities_module.TrackInfoModel
import kotlinx.coroutines.flow.Flow

public interface GetTracksWithDurationFilterUseCase {

    public operator fun invoke(): Flow<List<TrackInfoModel>>

}