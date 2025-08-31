package com.gab.model_media_usecases.di

import com.gab.model_media_usecases.usecases_api.*

public interface ModelMediaUseCasesApi {
    public fun getChangePositionUseCase(): ChangePositionUseCase
    public fun getGetCurrentPositionFlowUseCase(): GetCurrentPositionFlowUseCase
    public fun getGetCurrentTrackInfoFlowUseCase(): GetCurrentTrackInfoFlowUseCase
    public fun getGetIsRepeatingOneFlowUseCase(): GetIsRepeatingOneFlowUseCase
    public fun getIsInitializedUseCase(): IsInitializedUseCase
    public fun getGetIsShuffleModeSetFlowUseCase(): GetIsShuffleModeSetFlowUseCase
    public fun getGetIsTrackPlayingFlowUseCase(): GetIsTrackPlayingFlowUseCase
    public fun getIsRepeatingOneStateChangeUseCase(): IsRepeatingOneStateChangeUseCase
    public fun getNextTrackUseCase(): NextTrackUseCase
    public fun getPlayPauseStatusChangeUseCase(): PlayPauseStatusChangeUseCase
    public fun getPreviousTrackUseCase(): PreviousTrackUseCase
    public fun getSetNextTrackUseCase(): SetNextTrackUseCase
    public fun getSetTrackQueueUseCase(): SetTrackQueueUseCase
    public fun getSetRandomTrackQueueUseCase(): SetRandomTrackQueueUseCase
    public fun getShuffleStateChangeUseCase(): ShuffleStateChangeUseCase
}