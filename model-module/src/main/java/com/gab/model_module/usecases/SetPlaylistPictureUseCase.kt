package com.gab.model_module.usecases

import android.net.Uri
import com.gab.core_music_loading.models.PlaylistInfoModel

public interface SetPlaylistPictureUseCase {
    public suspend operator fun invoke(playlist: PlaylistInfoModel, uri: Uri)
}