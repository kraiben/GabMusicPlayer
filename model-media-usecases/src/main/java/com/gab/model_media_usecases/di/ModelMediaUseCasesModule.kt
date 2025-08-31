package com.gab.model_media_usecases.di

import com.gab.model_media_usecases.usecases_Impl.*
import com.gab.model_media_usecases.usecases_api.*
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