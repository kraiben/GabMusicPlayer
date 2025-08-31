package com.gab.model_module.usecases

import android.net.Uri
import com.gab.music_entities_module.TrackInfoModel

public interface ChangePlaylistUseCase  {
    public suspend operator fun invoke(
        playlistId: Long,
        tracks: List<TrackInfoModel>,
        title: String,
        coverUri: Uri,
    ): Boolean
}