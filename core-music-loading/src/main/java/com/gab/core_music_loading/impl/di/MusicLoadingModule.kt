package com.gab.core_music_loading.impl.di

import com.gab.core_music_loading.api.data.MusicLoadingRepository
import com.gab.core_music_loading.impl.data.MusicLoadingRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
internal interface MusicLoadingModule {
    @MusicLoadingScope
    @Binds
    fun bindMusicLoadingRepository(impl: MusicLoadingRepositoryImpl): MusicLoadingRepository
}