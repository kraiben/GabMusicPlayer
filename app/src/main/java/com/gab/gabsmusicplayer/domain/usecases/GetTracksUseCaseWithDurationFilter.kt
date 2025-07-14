package com.gab.gabsmusicplayer.domain.usecases

import com.gab.gabsmusicplayer.domain.models.TrackInfoModel
import com.gab.gabsmusicplayer.domain.repository.MusicLoadingRepository
import com.gab.gabsmusicplayer.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class GetTracksUseCaseWithDurationFilter @Inject constructor(
    private val loadingRepository: MusicLoadingRepository,
    private val settingsRepository: SettingsRepository,
) {

    operator fun invoke(): Flow<List<TrackInfoModel>> = combine(
        loadingRepository.getAllTracks(), settingsRepository.getMinDurationInSeconds()
    ) { tracks: List<TrackInfoModel>, duration: Long ->
        tracks.filter { it.duration >= duration * 1000 }
    }.distinctUntilChanged()

}