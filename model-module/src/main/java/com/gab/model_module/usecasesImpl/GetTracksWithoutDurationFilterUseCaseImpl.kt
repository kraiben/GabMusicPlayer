package com.gab.model_module.usecasesImpl

import com.gab.core_music_loading.api.data.MusicLoadingRepository
import com.gab.model_module.usecases.GetTracksWithoutDurationFilterUseCase
import com.gab.music_entities_module.TrackInfoModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

public class GetTracksWithoutDurationFilterUseCaseImpl @Inject constructor(
    private val loadingRepository: MusicLoadingRepository,
) : GetTracksWithoutDurationFilterUseCase {

    override operator fun invoke(): StateFlow<List<TrackInfoModel>> =
        loadingRepository.getAllTracks()

}