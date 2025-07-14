package com.gab.gabsmusicplayer.domain.usecases

import com.gab.gabsmusicplayer.domain.models.TrackInfoModel
import com.gab.gabsmusicplayer.domain.repository.MusicLoadingRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetTracksUseCaseWithoutDurationFilter @Inject constructor(
    private val loadingRepository: MusicLoadingRepository
) {

    operator fun invoke(): StateFlow<List<TrackInfoModel>> = loadingRepository.getAllTracks()

}