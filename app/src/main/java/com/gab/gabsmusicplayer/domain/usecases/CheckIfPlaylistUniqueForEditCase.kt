package com.gab.gabsmusicplayer.domain.usecases

import com.gab.gabsmusicplayer.data.repository.musicLoading.MusicLoadingRepositoryImpl
import javax.inject.Inject

class CheckIfPlaylistUniqueForEditCase @Inject constructor(
    private val repositoryImpl: MusicLoadingRepositoryImpl,
) {
    suspend operator fun invoke(title: String): Boolean =
        repositoryImpl.isTitleUniqueForCreation(title)
}