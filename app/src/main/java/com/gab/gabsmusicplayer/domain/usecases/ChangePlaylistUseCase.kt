package com.gab.gabsmusicplayer.domain.usecases

import android.net.Uri
import com.gab.gabsmusicplayer.domain.models.TrackInfoModel
import com.gab.gabsmusicplayer.domain.repository.MusicLoadingRepository
import javax.inject.Inject

class ChangePlaylistUseCase @Inject constructor(private val repository: MusicLoadingRepository) {
    suspend operator fun invoke(
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