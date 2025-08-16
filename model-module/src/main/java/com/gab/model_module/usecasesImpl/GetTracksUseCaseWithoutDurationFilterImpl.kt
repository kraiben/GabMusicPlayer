package com.gab.model_module.usecasesImpl

import com.gab.core_music_loading.api.data.MusicLoadingRepository
import com.gab.core_music_loading.models.TrackInfoModel
import com.gab.model_module.usecases.GetTracksUseCaseWithoutDurationFilter
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

public class GetTracksUseCaseWithoutDurationFilterImpl @Inject constructor(
    private val loadingRepository: MusicLoadingRepository,
) : GetTracksUseCaseWithoutDurationFilter {

    override operator fun invoke(): StateFlow<List<TrackInfoModel>> =
        loadingRepository.getAllTracks()

}