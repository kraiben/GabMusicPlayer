package com.gab.model_module.usecases

import android.net.Uri
import com.gab.core_music_loading.models.TrackInfoModel

public interface CreatePlaylistUseCase {
    public suspend operator fun invoke(tracks: List<TrackInfoModel>, title: String, coverUri: Uri): Boolean
}