package com.gab.model_module.usecasesImpl

import com.gab.core_music_loading.api.data.MusicLoadingRepository
import com.gab.model_module.usecases.GetPlaylistsUseCase
import com.gab.music_entities_module.PlaylistInfoModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

public class GetPlaylistsUseCaseImpl @Inject constructor(private val repository: MusicLoadingRepository) :
    GetPlaylistsUseCase {
    override operator fun invoke(): StateFlow<List<PlaylistInfoModel>> = repository.getPlaylists()
}