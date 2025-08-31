package com.gab.gabsmusicplayer.di.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gab.feature_all_tracks.ui.AllTracksViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
public interface ViewModelModule {
    @Binds
    public fun bindViewModelFactory(impl: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(AllTracksViewModel::class)
    public fun bindAllTracksViewModel(impl: AllTracksViewModel): ViewModel
}