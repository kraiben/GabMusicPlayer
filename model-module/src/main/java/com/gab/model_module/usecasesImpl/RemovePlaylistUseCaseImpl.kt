package com.gab.model_module.usecasesImpl

import com.gab.core_music_loading.api.data.MusicLoadingRepository
import com.gab.core_music_loading.models.PlaylistInfoModel
import com.gab.model_module.usecases.RemovePlaylistUseCase
import javax.inject.Inject

public class RemovePlaylistUseCaseImpl @Inject constructor(private val repository: MusicLoadingRepository) :
    RemovePlaylistUseCase {
    override suspend operator fun invoke(playlist: PlaylistInfoModel) {
        repository.removePlaylist(playlist)
    }
}