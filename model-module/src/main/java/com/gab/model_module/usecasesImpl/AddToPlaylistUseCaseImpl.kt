package com.gab.model_module.usecasesImpl

import com.gab.core_music_loading.api.data.MusicLoadingRepository
import com.gab.music_entities_module.TrackInfoModel
import com.gab.music_entities_module.PlaylistInfoModel
import com.gab.model_module.usecases.AddToPlaylistUseCase
import javax.inject.Inject

public class AddToPlaylistUseCaseImpl @Inject constructor(private val repository: MusicLoadingRepository) :
    AddToPlaylistUseCase {
    override suspend operator fun invoke(playlist: PlaylistInfoModel, track: TrackInfoModel) {
        repository.addToPlaylist(playlist, track)
    }
}