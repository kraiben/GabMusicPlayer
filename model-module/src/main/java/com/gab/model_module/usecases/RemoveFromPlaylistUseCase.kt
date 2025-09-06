package com.gab.model_module.usecases

import com.gab.music_entities_module.PlaylistInfoModel
import com.gab.music_entities_module.TrackInfoModel

public interface RemoveFromPlaylistUseCase {
    public suspend operator fun invoke(playlist: PlaylistInfoModel, track: TrackInfoModel)
}