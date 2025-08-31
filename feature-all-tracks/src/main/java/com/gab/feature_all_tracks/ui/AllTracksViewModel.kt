package com.gab.feature_all_tracks.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gab.model_media_usecases.usecases_api.*
import com.gab.model_module.usecases.*
import com.gab.music_entities_module.TrackInfoModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

public class AllTracksViewModel @Inject constructor(
    private val getTracksWithDurationFilterUseCase: GetTracksWithDurationFilterUseCase,
    private val setTracksQueueUseCase: SetTrackQueueUseCase,
    private val setRandomTrackQueueUseCase: SetRandomTrackQueueUseCase,
    private val setNextTrackUseCase: SetNextTrackUseCase,

    ) : ViewModel() {
    private val tracks = getTracksWithDurationFilterUseCase().map {
        AllTracksScreenState.Tracks(it) as AllTracksScreenState
    }.onStart { emit(AllTracksScreenState.Loading ) }

    internal fun getTracks(): Flow<AllTracksScreenState> = tracks

    internal fun setTrackQueue(tracks: List<TrackInfoModel>, startIndex: Int) {
        viewModelScope.launch {
            setTracksQueueUseCase(tracks = tracks, startIndex = startIndex)
        }
    }

    internal fun setRandomTracksQueue(tracks: List<TrackInfoModel>) {
        viewModelScope.launch {
            setRandomTrackQueueUseCase(tracks)
        }
    }
    internal fun setNextTrack(track: TrackInfoModel) {
        viewModelScope.launch {
            setNextTrackUseCase(track)
        }
    }

}