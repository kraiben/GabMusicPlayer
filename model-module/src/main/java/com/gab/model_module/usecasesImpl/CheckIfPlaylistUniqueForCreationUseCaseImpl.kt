package com.gab.model_module.usecasesImpl

import com.gab.core_music_loading.api.data.MusicLoadingRepository
import com.gab.model_module.usecases.CheckIfPlaylistUniqueForCreationUseCase
import javax.inject.Inject

public class CheckIfPlaylistUniqueForCreationUseCaseImpl @Inject constructor(
    private val repositoryImpl: MusicLoadingRepository,
) : CheckIfPlaylistUniqueForCreationUseCase {
    override suspend operator fun invoke(title: String): Boolean =
        repositoryImpl.isTitleUniqueForCreation(title)
}