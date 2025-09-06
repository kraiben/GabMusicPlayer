package com.gab.model_media_usecases.di

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
import com.gab.model_media_usecases.usecases_api.SetNextTrackUseCase
import com.gab.model_media_usecases.usecases_api.SetRandomTrackQueueUseCase
import com.gab.model_media_usecases.usecases_api.SetTrackQueueUseCase
import com.gab.model_media_usecases.usecases_api.ShuffleStateChangeUseCase

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