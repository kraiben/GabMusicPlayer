package com.gab.gabsmusicplayer.di

import androidx.lifecycle.ViewModel
import com.gab.gabsmusicplayer.ui.allTracksScreen.AllTracksViewModel
import com.gab.gabsmusicplayer.ui.screens.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @IntoMap
    @ViewModelKey(AllTracksViewModel::class)
    @Binds
    fun bindAllTracksViewModel(viewModel: AllTracksViewModel): ViewModel
}