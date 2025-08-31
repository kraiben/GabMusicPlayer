package com.gab.model_module.usecases

import com.gab.music_entities_module.PlaylistInfoModel

public interface RemovePlaylistUseCase  {
    public suspend operator fun invoke(playlist: PlaylistInfoModel)
}