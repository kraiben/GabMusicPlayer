package com.gab.model_module.usecases

import com.gab.music_entities_module.TrackInfoModel
import com.gab.music_entities_module.PlaylistInfoModel

public interface AddToPlaylistUseCase  {
    public suspend operator fun invoke(playlist: PlaylistInfoModel, track: TrackInfoModel)
}