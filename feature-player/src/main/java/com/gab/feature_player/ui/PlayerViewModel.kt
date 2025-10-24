package com.gab.feature_player.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gab.model_media_usecases.usecases_api.ChangePositionUseCase
import com.gab.model_media_usecases.usecases_api.GetCurrentPositionFlowUseCase
import com.gab.model_media_usecases.usecases_api.GetCurrentTrackInfoFlowUseCase
import com.gab.model_media_usecases.usecases_api.GetIsRepeatingOneFlowUseCase
import com.gab.model_media_usecases.usecases_api.GetIsShuffleModeSetFlowUseCase
import com.gab.model_media_usecases.usecases_api.GetIsTrackPlayingFlowUseCase
import com.gab.model_media_usecases.usecases_api.IsInitializedUseCase
import com.gab.model_media_usecases.usecases_api.IsRepeatingOneStateChangeUseCase
import com.gab.model_media_usecases.usecases_api.NextTrackUseCase
import com.gab.model_media_usecases.usecases_api.PlayPauseStatusChangeUseCase
import com.gab.model_media_usecases.usecases_api.PreviousTrackUseCase
import com.gab.model_media_usecases.usecases_api.ShuffleStateChangeUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

public class PlayerViewModel @Inject constructor(
    private val shuffleStateChangeUseCase: ShuffleStateChangeUseCase,
    private val changePositionUseCase: ChangePositionUseCase,
    private val getIsTrackPlayingFlowUseCase: GetIsTrackPlayingFlowUseCase,
    private val getIsShuffleModeSetFlowUseCase: GetIsShuffleModeSetFlowUseCase,
    private val getIsRepeatingOneFlowUseCase: GetIsRepeatingOneFlowUseCase,
    private val isInitializedUseCase: IsInitializedUseCase,
    private val isRepeatingOneStateChangeUseCase: IsRepeatingOneStateChangeUseCase,
    private val getCurrentPositionFlowUseCase: GetCurrentPositionFlowUseCase,
    private val previousTrackUseCase: PreviousTrackUseCase,
    private val nextTrackUseCase: NextTrackUseCase,
    private val getCurrentTrackInfoFlowUseCase: GetCurrentTrackInfoFlowUseCase,
    private val playPauseStatusChangeUseCase: PlayPauseStatusChangeUseCase,
) : ViewModel() {

    private val currentTrack = getCurrentTrackInfoFlowUseCase()
    internal fun getCurrentTrack() = currentTrack
    private val isInitialized = isInitializedUseCase()
    internal fun getIsInitializedFlow() = isInitialized
    private val currentPosition = getCurrentPositionFlowUseCase()
    internal fun getCurrentPosition() = currentPosition

    internal fun changePosition(position: Long) {
        viewModelScope.launch { changePositionUseCase(position) }
    }

    private val isShuffled = getIsShuffleModeSetFlowUseCase()
    internal fun getIsShuffledFlow() = isShuffled
    internal fun shuffleStateChange() {
        viewModelScope.launch { shuffleStateChangeUseCase() }
    }


    internal fun setPreviousTrack() {
        viewModelScope.launch {
            previousTrackUseCase()
        }
    }
    internal fun setNextTrack() {
        viewModelScope.launch {
            nextTrackUseCase()
        }
    }
    private val isPlaying = getIsTrackPlayingFlowUseCase()
    internal fun getPlayingStatus() = isPlaying
    internal fun playPauseStatusChange() {
        viewModelScope.launch {
            playPauseStatusChangeUseCase()
        }
    }
    private val isRepeatingOneFlow = getIsRepeatingOneFlowUseCase()
    internal fun getRepeatingOneState() = isRepeatingOneFlow
    internal fun isRepeatingOneStateChange() {
        viewModelScope.launch {
            isRepeatingOneStateChangeUseCase()
        }
    }
}