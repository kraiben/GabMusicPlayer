package com.gab.model_module.usecasesImpl

import android.net.Uri
import com.gab.core_music_loading.api.data.MusicLoadingRepository
import com.gab.music_entities_module.TrackInfoModel
import com.gab.model_module.usecases.ChangePlaylistUseCase
import javax.inject.Inject

public class ChangePlaylistUseCaseImpl @Inject constructor(private val repository: MusicLoadingRepository) :
    ChangePlaylistUseCase {
    override suspend operator fun invoke(
        playlistId: Long,
        tracks: List<TrackInfoModel>,
        title: String,
        coverUri: Uri,
    ): Boolean {
        val res = repository.isTitleUniqueForEdit(playlistId, title)
        if (res) repository.changePlaylistInfo(
            playlistId,
            tracks,
            title,
            coverUri
        )
        return res
    }
}