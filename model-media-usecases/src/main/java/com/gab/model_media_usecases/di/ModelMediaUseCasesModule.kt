package com.gab.model_media_usecases.di

import com.gab.model_media_usecases.usecases_Impl.ChangePositionUseCaseImpl
import com.gab.model_media_usecases.usecases_Impl.GetCurrentPositionFlowUseCaseImpl
import com.gab.model_media_usecases.usecases_Impl.GetCurrentTrackInfoFlowUseCaseImpl
import com.gab.model_media_usecases.usecases_Impl.GetIsRepeatingOneFlowUseCaseImpl
import com.gab.model_media_usecases.usecases_Impl.GetIsShuffleModeSetFlowUseCaseImpl
import com.gab.model_media_usecases.usecases_Impl.GetIsTrackPlayingFlowUseCaseImpl
import com.gab.model_media_usecases.usecases_Impl.IsInitializedUseCaseImpl
import com.gab.model_media_usecases.usecases_Impl.IsRepeatingOneStateChangeUseCaseImpl
import com.gab.model_media_usecases.usecases_Impl.NextTrackUseCaseImpl
import com.gab.model_media_usecases.usecases_Impl.PlayPauseStatusChangeUseCaseImpl
import com.gab.model_media_usecases.usecases_Impl.PreviousTrackUseCaseImpl
import com.gab.model_media_usecases.usecases_Impl.SetNextTrackUseCaseImpl
import com.gab.model_media_usecases.usecases_Impl.SetRandomTrackQueueUseCaseImpl
import com.gab.model_media_usecases.usecases_Impl.SetTrackQueueUseCaseImpl
import com.gab.model_media_usecases.usecases_Impl.ShuffleStateChangeUseCaseImpl
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
import dagger.Binds
import dagger.Module

@Module
public interface ModelMediaUseCasesModule {

    @Binds
    public fun bindChangePositionUseCase(impl: ChangePositionUseCaseImpl): ChangePositionUseCase
    @Binds
    public fun bindGetCurrentPositionFlowUseCase(impl: GetCurrentPositionFlowUseCaseImpl): GetCurrentPositionFlowUseCase
    @Binds
    public fun bindGetCurrentTrackInfoFlowUseCase(impl: GetCurrentTrackInfoFlowUseCaseImpl): GetCurrentTrackInfoFlowUseCase
    @Binds
    public fun bindGetIsRepeatingOneFlowUseCase(impl: GetIsRepeatingOneFlowUseCaseImpl): GetIsRepeatingOneFlowUseCase
    @Binds
    public fun bindGetIsShuffleModeSetFlowUseCase(impl: GetIsShuffleModeSetFlowUseCaseImpl): GetIsShuffleModeSetFlowUseCase
    @Binds
    public fun bindIsInitializedUseCase(impl: IsInitializedUseCaseImpl): IsInitializedUseCase
    @Binds
    public fun bindGetIsTrackPlayingFlowUseCase(impl: GetIsTrackPlayingFlowUseCaseImpl): GetIsTrackPlayingFlowUseCase
    @Binds
    public fun bindIsRepeatingOneStateChangeUseCase(impl: IsRepeatingOneStateChangeUseCaseImpl): IsRepeatingOneStateChangeUseCase
    @Binds
    public fun bindNextTrackUseCase(impl: NextTrackUseCaseImpl): NextTrackUseCase
    @Binds
    public fun bindPlayPauseStatusChangeUseCase(impl: PlayPauseStatusChangeUseCaseImpl): PlayPauseStatusChangeUseCase
    @Binds
    public fun bindPreviousTrackUseCase(impl: PreviousTrackUseCaseImpl): PreviousTrackUseCase
    @Binds
    public fun bindSetNextTrackUseCase(impl: SetNextTrackUseCaseImpl): SetNextTrackUseCase
    @Binds
    public fun bindSetTrackQueueUseCase(impl: SetTrackQueueUseCaseImpl): SetTrackQueueUseCase
    @Binds
    public fun bindSetRandomTrackQueueUseCase(impl: SetRandomTrackQueueUseCaseImpl): SetRandomTrackQueueUseCase
    @Binds
    public fun bindShuffleStateChangeUseCase(impl: ShuffleStateChangeUseCaseImpl): ShuffleStateChangeUseCase

}