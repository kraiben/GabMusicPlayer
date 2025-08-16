package com.gab.gabsmusicplayer.di

import com.gab.model_module.di.UseCasesComponent
import com.gab.model_module.usecases.AddToPlaylistUseCase
import com.gab.model_module.usecases.ChangePlaylistUseCase
import com.gab.model_module.usecases.CheckIfPlaylistUniqueForCreationUseCase
import com.gab.model_module.usecases.CheckIfPlaylistUniqueForEditCase
import com.gab.model_module.usecases.CreatePlaylistUseCase
import com.gab.model_module.usecases.DecrementDurationUseCase
import com.gab.model_module.usecases.GetMinDurationInSecondsUseCase
import com.gab.model_module.usecases.GetPlaylistsUseCase
import com.gab.model_module.usecases.GetTracksUseCaseWithDurationFilter
import com.gab.model_module.usecases.GetTracksUseCaseWithoutDurationFilter
import com.gab.model_module.usecases.IncrementDurationUseCase
import com.gab.model_module.usecases.IsDarkThemeChangeUseCase
import com.gab.model_module.usecases.IsDarkThemeUseCase
import com.gab.model_module.usecases.RemoveFromPlaylistUseCase
import com.gab.model_module.usecases.RemovePlaylistUseCase
import com.gab.model_module.usecases.SetPlaylistPictureUseCase
import com.gab.model_module.usecases.UpdateUseCase
import dagger.Module
import dagger.Provides

@Module
class AppUseCasesModule {

    @Provides
    fun provideAddToPlaylistUseCase(): AddToPlaylistUseCase =
        UseCasesComponent.get().getAddToPlaylistUseCase()
    @Provides
    fun provideChangePlaylistUseCase(): ChangePlaylistUseCase =
        UseCasesComponent.get().getChangePlaylistUseCase()
    @Provides
    fun provideCheckIfPlaylistUniqueForCreationUseCase(): CheckIfPlaylistUniqueForCreationUseCase =
        UseCasesComponent.get().getCheckIfPlaylistUniqueForCreationUseCase()
    @Provides
    fun provideCheckIfPlaylistUniqueForEditCase(): CheckIfPlaylistUniqueForEditCase =
        UseCasesComponent.get().getCheckIfPlaylistUniqueForEditCase()
    @Provides
    fun provideCreatePlaylistUseCase(): CreatePlaylistUseCase =
        UseCasesComponent.get().getCreatePlaylistUseCase()
    @Provides
    fun provideDecrementDurationUseCase(): DecrementDurationUseCase =
        UseCasesComponent.get().getDecrementDurationUseCase()
    @ApplicationScope
    @Provides
    fun provideGetMinDurationInSecondsUseCase(): GetMinDurationInSecondsUseCase =
        UseCasesComponent.get().getGetMinDurationInSecondsUseCase()
    @ApplicationScope
    @Provides
    fun provideGetPlaylistsUseCase(): GetPlaylistsUseCase =
        UseCasesComponent.get().getGetPlaylistsUseCase()
    @ApplicationScope
    @Provides
    fun provideGetTracksUseCaseWithDurationFilter(): GetTracksUseCaseWithDurationFilter =
        UseCasesComponent.get().getGetTracksUseCaseWithDurationFilter()
    @ApplicationScope
    @Provides
    fun provideGetTracksUseCaseWithoutDurationFilter(): GetTracksUseCaseWithoutDurationFilter =
        UseCasesComponent.get().getGetTracksUseCaseWithoutDurationFilter()
    @Provides
    fun provideIncrementDurationUseCase(): IncrementDurationUseCase =
        UseCasesComponent.get().getIncrementDurationUseCase()
    @Provides
    fun provideIsDarkThemeChangeUseCase(): IsDarkThemeChangeUseCase =
        UseCasesComponent.get().getIsDarkThemeChangeUseCase()
    @Provides
    fun provideIsDarkThemeUseCase(): IsDarkThemeUseCase =
        UseCasesComponent.get().getIsDarkThemeUseCase()
    @Provides
    fun provideRemoveFromPlaylistUseCase(): RemoveFromPlaylistUseCase =
        UseCasesComponent.get().getRemoveFromPlaylistUseCase()
    @Provides
    fun provideRemovePlaylistUseCase(): RemovePlaylistUseCase =
        UseCasesComponent.get().getRemovePlaylistUseCase()
    @Provides
    fun provideSetPlaylistPictureUseCase(): SetPlaylistPictureUseCase =
        UseCasesComponent.get().getSetPlaylistPictureUseCase()
    @Provides
    fun provideUpdateUseCase(): UpdateUseCase =
        UseCasesComponent.get().getUpdateUseCase()
}