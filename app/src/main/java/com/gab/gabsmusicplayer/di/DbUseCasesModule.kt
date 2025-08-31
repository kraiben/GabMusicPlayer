package com.gab.gabsmusicplayer.di

import com.gab.model_module.di.DbUseCasesComponent
import com.gab.model_module.usecases.AddToPlaylistUseCase
import com.gab.model_module.usecases.ChangePlaylistUseCase
import com.gab.model_module.usecases.CheckIfPlaylistUniqueForCreationUseCase
import com.gab.model_module.usecases.CheckIfPlaylistUniqueForEditCase
import com.gab.model_module.usecases.CreatePlaylistUseCase
import com.gab.model_module.usecases.DecrementDurationUseCase
import com.gab.model_module.usecases.GetMinDurationInSecondsUseCase
import com.gab.model_module.usecases.GetPlaylistsUseCase
import com.gab.model_module.usecases.GetTracksWithDurationFilterUseCase
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
class DbUseCasesModule {

    @Provides
    fun provideAddToPlaylistUseCase(): AddToPlaylistUseCase =
        DbUseCasesComponent.get().getAddToPlaylistUseCase()
    @Provides
    fun provideChangePlaylistUseCase(): ChangePlaylistUseCase =
        DbUseCasesComponent.get().getChangePlaylistUseCase()
    @Provides
    fun provideCheckIfPlaylistUniqueForCreationUseCase(): CheckIfPlaylistUniqueForCreationUseCase =
        DbUseCasesComponent.get().getCheckIfPlaylistUniqueForCreationUseCase()
    @Provides
    fun provideCheckIfPlaylistUniqueForEditCase(): CheckIfPlaylistUniqueForEditCase =
        DbUseCasesComponent.get().getCheckIfPlaylistUniqueForEditCase()
    @Provides
    fun provideCreatePlaylistUseCase(): CreatePlaylistUseCase =
        DbUseCasesComponent.get().getCreatePlaylistUseCase()
    @Provides
    fun provideDecrementDurationUseCase(): DecrementDurationUseCase =
        DbUseCasesComponent.get().getDecrementDurationUseCase()
    @ApplicationScope
    @Provides
    fun provideGetMinDurationInSecondsUseCase(): GetMinDurationInSecondsUseCase =
        DbUseCasesComponent.get().getGetMinDurationInSecondsUseCase()
    @ApplicationScope
    @Provides
    fun provideGetPlaylistsUseCase(): GetPlaylistsUseCase =
        DbUseCasesComponent.get().getGetPlaylistsUseCase()
    @ApplicationScope
    @Provides
    fun provideGetTracksUseCaseWithDurationFilter(): GetTracksWithDurationFilterUseCase =
        DbUseCasesComponent.get().getGetTracksUseCaseWithDurationFilter()
    @ApplicationScope
    @Provides
    fun provideGetTracksUseCaseWithoutDurationFilter(): GetTracksUseCaseWithoutDurationFilter =
        DbUseCasesComponent.get().getGetTracksUseCaseWithoutDurationFilter()
    @Provides
    fun provideIncrementDurationUseCase(): IncrementDurationUseCase =
        DbUseCasesComponent.get().getIncrementDurationUseCase()
    @Provides
    fun provideIsDarkThemeChangeUseCase(): IsDarkThemeChangeUseCase =
        DbUseCasesComponent.get().getIsDarkThemeChangeUseCase()
    @Provides
    @ApplicationScope
    fun provideIsDarkThemeUseCase(): IsDarkThemeUseCase =
        DbUseCasesComponent.get().getIsDarkThemeUseCase()
    @Provides
    fun provideRemoveFromPlaylistUseCase(): RemoveFromPlaylistUseCase =
        DbUseCasesComponent.get().getRemoveFromPlaylistUseCase()
    @Provides
    fun provideRemovePlaylistUseCase(): RemovePlaylistUseCase =
        DbUseCasesComponent.get().getRemovePlaylistUseCase()
    @Provides
    fun provideSetPlaylistPictureUseCase(): SetPlaylistPictureUseCase =
        DbUseCasesComponent.get().getSetPlaylistPictureUseCase()
    @Provides
    fun provideUpdateUseCase(): UpdateUseCase =
        DbUseCasesComponent.get().getUpdateUseCase()
}