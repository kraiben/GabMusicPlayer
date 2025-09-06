package com.gab.model_module.usecasesImpl

import com.gab.core_music_loading.api.data.MusicLoadingRepository
import com.gab.model_module.usecases.RemoveFromPlaylistUseCase
import com.gab.music_entities_module.PlaylistInfoModel
import com.gab.music_entities_module.TrackInfoModel
import javax.inject.Inject

public class RemoveFromPlaylistUseCaseImpl @Inject constructor(private val repository: MusicLoadingRepository) :
    RemoveFromPlaylistUseCase {
    override suspend operator fun invoke(playlist: PlaylistInfoModel, track: TrackInfoModel) {
        repository.removeFromPlaylist(playlist, track)
    }
}