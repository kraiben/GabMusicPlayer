package com.gab.model_module.usecasesImpl

import com.gab.core_music_loading.api.data.MusicLoadingRepository
import com.gab.core_music_loading.models.TrackInfoModel
import com.gab.core_settings.api.data.SettingsRepository
import com.gab.model_module.usecases.GetTracksUseCaseWithDurationFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

public class GetTracksUseCaseWithDurationFilterImpl @Inject constructor(
    private val loadingRepository: MusicLoadingRepository,
    private val settingsRepository: SettingsRepository,
) : GetTracksUseCaseWithDurationFilter {

    override operator fun invoke(): Flow<List<TrackInfoModel>> = combine(
        loadingRepository.getAllTracks(), settingsRepository.getMinDurationInSeconds()
    ) { tracks: List<TrackInfoModel>, duration: Long ->
        tracks.filter { it.duration >= duration * 1000 }
    }.distinctUntilChanged()

}