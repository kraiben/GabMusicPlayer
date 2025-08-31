package com.gab.model_module.usecases

import android.net.Uri
import com.gab.music_entities_module.PlaylistInfoModel

public interface SetPlaylistPictureUseCase {
    public suspend operator fun invoke(playlist: PlaylistInfoModel, uri: Uri)
}