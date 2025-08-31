package com.gab.feature_all_tracks.di

import androidx.lifecycle.ViewModelProvider
import com.gab.model_media_usecases.usecases_api.SetNextTrackUseCase
import com.gab.model_media_usecases.usecases_api.SetRandomTrackQueueUseCase
import com.gab.model_media_usecases.usecases_api.SetTrackQueueUseCase
import com.gab.model_module.usecases.GetTracksWithDurationFilterUseCase

public interface AllTracksFeatureDependencies {

    public val viewModelFactory: ViewModelProvider.Factory
//    public val getTracksWithDurationFilterUseCase: GetTracksWithDurationFilterUseCase
//    public val setTracksQueueUseCase: SetTrackQueueUseCase
//    public val setRandomTrackQueueUseCase: SetRandomTrackQueueUseCase
//    public val setNextTrackUseCase: SetNextTrackUseCase

}