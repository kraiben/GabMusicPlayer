package com.gab.testapp.di

import com.gab.core_media.api.MediaPlayerRepository
import com.gab.core_media.api.TracksListRepository
import com.gab.core_media.di.CoreMediaComponent
import dagger.Module
import dagger.Provides

@Module
class TestAppModule {
    @Provides
    fun provideMediaPlayerRepository(): MediaPlayerRepository{
        return CoreMediaComponent.get().getMediaPlayerRepository()
    }
    @Provides
    fun provideTracksListRepository(): TracksListRepository {
        return CoreMediaComponent.get().getTracksListRepository()
    }
}