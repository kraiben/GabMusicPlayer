package com.gab.gabsmusicplayer.domain.usecases

import android.net.Uri
import com.gab.gabsmusicplayer.domain.models.PlaylistInfoModel
import com.gab.gabsmusicplayer.domain.repository.MusicLoadingRepository
import javax.inject.Inject

class SetPlaylistPictureUseCase @Inject constructor(private val repository: MusicLoadingRepository) {
    suspend operator fun invoke(playlist: PlaylistInfoModel, uri: Uri) {
        repository.setPlaylistPicture(playlist, uri)
    }
}