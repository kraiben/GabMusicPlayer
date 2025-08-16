package com.gab.model_module.usecasesImpl

import com.gab.core_music_loading.api.data.MusicLoadingRepository
import com.gab.core_music_loading.models.PlaylistInfoModel
import com.gab.core_music_loading.models.TrackInfoModel
import com.gab.model_module.usecases.RemoveFromPlaylistUseCase
import javax.inject.Inject

public class RemoveFromPlaylistUseCaseImpl @Inject constructor(private val repository: MusicLoadingRepository) :
    RemoveFromPlaylistUseCase {
    override suspend operator fun invoke(playlist: PlaylistInfoModel, track: TrackInfoModel) {
        repository.removeFromPlaylist(playlist, track)
    }
}