package com.gab.gabsmusicplayer.domain.usecases

import com.gab.gabsmusicplayer.domain.models.PlaylistInfoModel
import com.gab.gabsmusicplayer.domain.repository.MusicLoadingRepository
import javax.inject.Inject

class RemovePlaylistUseCase @Inject constructor(private val repository: MusicLoadingRepository) {
    suspend operator fun invoke(playlist: PlaylistInfoModel) {
        repository.removePlaylist(playlist)
    }
}