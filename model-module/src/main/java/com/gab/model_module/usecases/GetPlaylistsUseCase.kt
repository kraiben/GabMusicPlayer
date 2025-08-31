package com.gab.model_module.usecases

import com.gab.music_entities_module.PlaylistInfoModel
import kotlinx.coroutines.flow.StateFlow

public interface GetPlaylistsUseCase {
    public operator fun invoke(): StateFlow<List<PlaylistInfoModel>>
}