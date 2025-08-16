package com.gab.model_module.usecases

import com.gab.core_music_loading.models.PlaylistInfoModel

public interface RemovePlaylistUseCase  {
    public suspend operator fun invoke(playlist: PlaylistInfoModel)
}