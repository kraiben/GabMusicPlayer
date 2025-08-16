package com.gab.model_module.usecases

import com.gab.core_music_loading.models.PlaylistInfoModel
import kotlinx.coroutines.flow.StateFlow

public interface GetPlaylistsUseCase {
    public operator fun invoke(): StateFlow<List<PlaylistInfoModel>>
}