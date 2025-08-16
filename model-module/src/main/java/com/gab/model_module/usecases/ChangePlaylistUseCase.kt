package com.gab.model_module.usecases

import android.net.Uri
import com.gab.core_music_loading.models.TrackInfoModel

public interface ChangePlaylistUseCase  {
    public suspend operator fun invoke(
        playlistId: Long,
        tracks: List<TrackInfoModel>,
        title: String,
        coverUri: Uri,
    ): Boolean
}