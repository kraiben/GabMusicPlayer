package com.gab.model_module.usecasesImpl

import android.net.Uri
import com.gab.core_music_loading.api.data.MusicLoadingRepository
import com.gab.model_module.usecases.SetPlaylistPictureUseCase
import com.gab.music_entities_module.PlaylistInfoModel
import javax.inject.Inject

public class SetPlaylistPictureUseCaseImpl @Inject constructor(private val repository: MusicLoadingRepository) :
    SetPlaylistPictureUseCase {
    override suspend operator fun invoke(playlist: PlaylistInfoModel, uri: Uri) {
        repository.setPlaylistPicture(playlist, uri)
    }
}