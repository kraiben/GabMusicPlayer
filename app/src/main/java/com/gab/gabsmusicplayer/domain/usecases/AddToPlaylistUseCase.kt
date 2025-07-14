package com.gab.gabsmusicplayer.domain.usecases

import com.gab.gabsmusicplayer.domain.models.PlaylistInfoModel
import com.gab.gabsmusicplayer.domain.models.TrackInfoModel
import com.gab.gabsmusicplayer.domain.repository.MusicLoadingRepository
import javax.inject.Inject

class AddToPlaylistUseCase @Inject constructor(private val repository: MusicLoadingRepository) {
    suspend operator fun invoke(playlist: PlaylistInfoModel, track: TrackInfoModel) {
        repository.addToPlaylist(playlist, track)
    }
}