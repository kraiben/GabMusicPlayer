package com.gab.model_media_usecases.usecases_api

import com.gab.music_entities_module.TrackInfoModel
import javax.inject.Inject

public interface SetNextTrackUseCase {
    public suspend operator fun invoke(track: TrackInfoModel)
}