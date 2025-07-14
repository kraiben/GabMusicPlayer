package com.gab.gabsmusicplayer.domain.usecases

import android.net.Uri
import com.gab.gabsmusicplayer.domain.models.TrackInfoModel
import com.gab.gabsmusicplayer.domain.repository.MusicLoadingRepository
import javax.inject.Inject

class CreatePlaylistUseCase @Inject constructor(private val repository: MusicLoadingRepository) {
    suspend operator fun invoke(tracks: List<TrackInfoModel>, title: String, coverUri: Uri): Boolean {
        val res = repository.isTitleUniqueForCreation(title)
        if (res) repository.createPlaylist(tracks, title, coverUri)
        return res
    }
}