package com.gab.gabsmusicplayer.di

import com.gab.model_media_usecases.di.ModelMediaUseCasesComponent
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
import dagger.Module
import dagger.Provides

@Module
class MediaUseCasesModule {

    @Provides
    fun provideChangePositionUseCase(): ChangePositionUseCase =
        ModelMediaUseCasesComponent.get().getChangePositionUseCase()

    @Provides
    fun provideGetCurrentPositionFlowUseCase(): GetCurrentPositionFlowUseCase =
        ModelMediaUseCasesComponent.get().getGetCurrentPositionFlowUseCase()

    @Provides
    fun provideGetCurrentTrackInfoFlowUseCase(): GetCurrentTrackInfoFlowUseCase =
        ModelMediaUseCasesComponent.get().getGetCurrentTrackInfoFlowUseCase()

    @Provides
    fun provideGetIsRepeatingOneFlowUseCase(): GetIsRepeatingOneFlowUseCase =
        ModelMediaUseCasesComponent.get().getGetIsRepeatingOneFlowUseCase()
    @Provides
    fun provideIsInitializedUseCase(): IsInitializedUseCase =
        ModelMediaUseCasesComponent.get().getIsInitializedUseCase()

    @Provides
    fun provideGetIsShuffleModeSetFlowUseCase(): GetIsShuffleModeSetFlowUseCase =
        ModelMediaUseCasesComponent.get().getGetIsShuffleModeSetFlowUseCase()

    @Provides
    fun provideGetIsTrackPlayingFlowUseCase(): GetIsTrackPlayingFlowUseCase =
        ModelMediaUseCasesComponent.get().getGetIsTrackPlayingFlowUseCase()

    @Provides
    fun provideIsRepeatingOneStateChangeUseCase(): IsRepeatingOneStateChangeUseCase =
        ModelMediaUseCasesComponent.get().getIsRepeatingOneStateChangeUseCase()

    @Provides
    fun provideNextTrackUseCase(): NextTrackUseCase =
        ModelMediaUseCasesComponent.get().getNextTrackUseCase()

    @Provides
    fun providePlayPauseStatusChangeUseCase(): PlayPauseStatusChangeUseCase =
        ModelMediaUseCasesComponent.get().getPlayPauseStatusChangeUseCase()

    @Provides
    fun providePreviousTrackUseCase(): PreviousTrackUseCase =
        ModelMediaUseCasesComponent.get().getPreviousTrackUseCase()

    @Provides
    fun provideSetNextTrackUseCase(): SetNextTrackUseCase =
        ModelMediaUseCasesComponent.get().getSetNextTrackUseCase()

    @Provides
    fun provideSetTrackQueueUseCase(): SetTrackQueueUseCase =
        ModelMediaUseCasesComponent.get().getSetTrackQueueUseCase()

    @Provides
    fun provideSetRandomTrackQueueUseCase(): SetRandomTrackQueueUseCase =
        ModelMediaUseCasesComponent.get().getSetRandomTrackQueueUseCase()

    @Provides
    fun provideShuffleStateChangeUseCase(): ShuffleStateChangeUseCase =
        ModelMediaUseCasesComponent.get().getShuffleStateChangeUseCase()

}