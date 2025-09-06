package com.gab.model_module.di

import com.gab.model_module.usecases.AddToPlaylistUseCase
import com.gab.model_module.usecases.ChangePlaylistUseCase
import com.gab.model_module.usecases.CheckIfPlaylistUniqueForCreationUseCase
import com.gab.model_module.usecases.CheckIfPlaylistUniqueForEditCase
import com.gab.model_module.usecases.CreatePlaylistUseCase
import com.gab.model_module.usecases.DecrementDurationUseCase
import com.gab.model_module.usecases.GetMinDurationInSecondsUseCase
import com.gab.model_module.usecases.GetPlaylistsUseCase
import com.gab.model_module.usecases.GetTracksWithoutDurationFilterUseCase
import com.gab.model_module.usecases.GetTracksWithDurationFilterUseCase
import com.gab.model_module.usecases.IncrementDurationUseCase
import com.gab.model_module.usecases.IsDarkThemeChangeUseCase
import com.gab.model_module.usecases.IsDarkThemeUseCase
import com.gab.model_module.usecases.RemoveFromPlaylistUseCase
import com.gab.model_module.usecases.RemovePlaylistUseCase
import com.gab.model_module.usecases.SetPlaylistPictureUseCase
import com.gab.model_module.usecases.UpdateUseCase
import com.gab.model_module.usecasesImpl.AddToPlaylistUseCaseImpl
import com.gab.model_module.usecasesImpl.ChangePlaylistUseCaseImpl
import com.gab.model_module.usecasesImpl.CheckIfPlaylistUniqueForCreationUseCaseImpl
import com.gab.model_module.usecasesImpl.CheckIfPlaylistUniqueForEditCaseImpl
import com.gab.model_module.usecasesImpl.CreatePlaylistUseCaseImpl
import com.gab.model_module.usecasesImpl.DecrementDurationUseCaseImpl
import com.gab.model_module.usecasesImpl.GetMinDurationInSecondsUseCaseImpl
import com.gab.model_module.usecasesImpl.GetPlaylistsUseCaseImpl
import com.gab.model_module.usecasesImpl.GetTracksWithoutDurationFilterUseCaseImpl
import com.gab.model_module.usecasesImpl.GetTracksWithDurationFilterUseCaseImpl
import com.gab.model_module.usecasesImpl.IncrementDurationUseCaseImpl
import com.gab.model_module.usecasesImpl.IsDarkThemeChangeUseCaseImpl
import com.gab.model_module.usecasesImpl.IsDarkThemeUseCaseImpl
import com.gab.model_module.usecasesImpl.RemoveFromPlaylistUseCaseImpl
import com.gab.model_module.usecasesImpl.RemovePlaylistUseCaseImpl
import com.gab.model_module.usecasesImpl.SetPlaylistPictureUseCaseImpl
import com.gab.model_module.usecasesImpl.UpdateUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
public interface UseCasesModule {


    @Binds
    @UseCasesScope
    public fun bindAddToPlaylistUseCase(impl: AddToPlaylistUseCaseImpl): AddToPlaylistUseCase

    @Binds
    @UseCasesScope
    public fun bindChangePlaylistUseCase(impl: ChangePlaylistUseCaseImpl): ChangePlaylistUseCase

    @Binds
    @UseCasesScope
    public fun bindCheckIfPlaylistUniqueForCreationUseCase(impl: CheckIfPlaylistUniqueForCreationUseCaseImpl): CheckIfPlaylistUniqueForCreationUseCase

    @Binds
    @UseCasesScope
    public fun bindCheckIfPlaylistUniqueForEditCase(impl: CheckIfPlaylistUniqueForEditCaseImpl): CheckIfPlaylistUniqueForEditCase

    @Binds
    @UseCasesScope
    public fun bindCreatePlaylistUseCase(impl: CreatePlaylistUseCaseImpl): CreatePlaylistUseCase

    @Binds
    @UseCasesScope
    public fun bindDecrementDurationUseCase(impl: DecrementDurationUseCaseImpl): DecrementDurationUseCase

    @Binds
    @UseCasesScope
    public fun bindGetMinDurationInSecondsUseCase(impl: GetMinDurationInSecondsUseCaseImpl): GetMinDurationInSecondsUseCase

    @Binds
    @UseCasesScope
    public fun bindGetPlaylistsUseCase(impl: GetPlaylistsUseCaseImpl): GetPlaylistsUseCase

    @Binds
    @UseCasesScope
    public fun bindGetTracksUseCaseWithDurationFilter(impl: GetTracksWithDurationFilterUseCaseImpl): GetTracksWithDurationFilterUseCase

    @Binds
    @UseCasesScope
    public fun bindGetTracksUseCaseWithoutDurationFilter(impl: GetTracksWithoutDurationFilterUseCaseImpl): GetTracksWithoutDurationFilterUseCase

    @Binds
    @UseCasesScope
    public fun bindIncrementDurationUseCase(impl: IncrementDurationUseCaseImpl): IncrementDurationUseCase

    @Binds
    @UseCasesScope
    public fun bindIsDarkThemeChangeUseCase(impl: IsDarkThemeChangeUseCaseImpl): IsDarkThemeChangeUseCase

    @Binds
    @UseCasesScope
    public fun bindIsDarkThemeUseCase(impl: IsDarkThemeUseCaseImpl): IsDarkThemeUseCase

    @Binds
    @UseCasesScope
    public fun bindRemoveFromPlaylistUseCase(impl: RemoveFromPlaylistUseCaseImpl): RemoveFromPlaylistUseCase

    @Binds
    @UseCasesScope
    public fun bindRemovePlaylistUseCase(impl: RemovePlaylistUseCaseImpl): RemovePlaylistUseCase

    @Binds
    @UseCasesScope
    public fun bindSetPlaylistPictureUseCase(impl: SetPlaylistPictureUseCaseImpl): SetPlaylistPictureUseCase

    @Binds
    @UseCasesScope
    public fun bindUpdateUseCase(impl: UpdateUseCaseImpl): UpdateUseCase

}