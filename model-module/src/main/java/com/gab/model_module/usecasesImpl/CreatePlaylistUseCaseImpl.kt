package com.gab.model_module.usecasesImpl

import android.net.Uri
import com.gab.core_music_loading.api.data.MusicLoadingRepository
import com.gab.music_entities_module.TrackInfoModel
import com.gab.model_module.usecases.CreatePlaylistUseCase
import javax.inject.Inject

public class CreatePlaylistUseCaseImpl @Inject constructor(private val repository: MusicLoadingRepository) :
    CreatePlaylistUseCase {
    override suspend operator fun invoke(
        tracks: List<TrackInfoModel>,
        title: String,
        coverUri: Uri,
    ): Boolean {
        val res = repository.isTitleUniqueForCreation(title)
        if (res) repository.createPlaylist(tracks, title, coverUri)
        return res
    }
}