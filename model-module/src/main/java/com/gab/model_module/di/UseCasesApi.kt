package com.gab.model_module.di

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

public interface UseCasesApi {

    public fun getAddToPlaylistUseCase(): AddToPlaylistUseCase
    public fun getChangePlaylistUseCase(): ChangePlaylistUseCase
    public fun getCheckIfPlaylistUniqueForCreationUseCase(): CheckIfPlaylistUniqueForCreationUseCase
    public fun getCheckIfPlaylistUniqueForEditCase(): CheckIfPlaylistUniqueForEditCase
    public fun getCreatePlaylistUseCase(): CreatePlaylistUseCase
    public fun getDecrementDurationUseCase(): DecrementDurationUseCase
    public fun getGetMinDurationInSecondsUseCase(): GetMinDurationInSecondsUseCase
    public fun getGetPlaylistsUseCase(): GetPlaylistsUseCase
    public fun getGetTracksUseCaseWithDurationFilter(): GetTracksUseCaseWithDurationFilter
    public fun getGetTracksUseCaseWithoutDurationFilter(): GetTracksUseCaseWithoutDurationFilter
    public fun getIncrementDurationUseCase(): IncrementDurationUseCase
    public fun getIsDarkThemeChangeUseCase(): IsDarkThemeChangeUseCase
    public fun getIsDarkThemeUseCase(): IsDarkThemeUseCase
    public fun getRemoveFromPlaylistUseCase(): RemoveFromPlaylistUseCase
    public fun getRemovePlaylistUseCase(): RemovePlaylistUseCase
    public fun getSetPlaylistPictureUseCase(): SetPlaylistPictureUseCase
    public fun getUpdateUseCase(): UpdateUseCase

}