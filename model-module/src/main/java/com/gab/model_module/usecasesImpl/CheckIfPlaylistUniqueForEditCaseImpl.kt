package com.gab.model_module.usecasesImpl

import com.gab.core_music_loading.api.data.MusicLoadingRepository
import com.gab.model_module.usecases.CheckIfPlaylistUniqueForEditCase
import javax.inject.Inject

public class CheckIfPlaylistUniqueForEditCaseImpl @Inject constructor(
    private val repositoryImpl: MusicLoadingRepository,
) : CheckIfPlaylistUniqueForEditCase {
    override suspend operator fun invoke(title: String): Boolean =
        repositoryImpl.isTitleUniqueForCreation(title)
}