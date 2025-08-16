package com.gab.model_module.usecases

import com.gab.core_music_loading.models.PlaylistInfoModel
import com.gab.core_music_loading.models.TrackInfoModel

public interface RemoveFromPlaylistUseCase {
    public suspend operator fun invoke(playlist: PlaylistInfoModel, track: TrackInfoModel)
}