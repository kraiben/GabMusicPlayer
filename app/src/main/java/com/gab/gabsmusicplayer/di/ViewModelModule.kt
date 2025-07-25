package com.gab.gabsmusicplayer.di

import androidx.lifecycle.ViewModel
import com.gab.gabsmusicplayer.ui.playlistScreens.MainViewModel
import com.gab.gabsmusicplayer.ui.screens.main.MusicViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(MusicViewModel::class)
    @Binds
    fun bindMainViewModel(viewModel: MusicViewModel): ViewModel

    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindPlaylistsViewModel(viewModel: MainViewModel): ViewModel
}