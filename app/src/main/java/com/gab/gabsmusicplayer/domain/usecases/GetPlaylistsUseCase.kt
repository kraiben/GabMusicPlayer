package com.gab.gabsmusicplayer.domain.usecases

import com.gab.gabsmusicplayer.domain.models.PlaylistInfoModel
import com.gab.gabsmusicplayer.domain.repository.MusicLoadingRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetPlaylistsUseCase @Inject constructor(private val repository: MusicLoadingRepository) {
    operator fun invoke(): StateFlow<List<PlaylistInfoModel>> = repository.getPlaylists()
}