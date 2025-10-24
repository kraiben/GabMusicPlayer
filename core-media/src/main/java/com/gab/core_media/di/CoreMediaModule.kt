package com.gab.core_media.di

import com.gab.core_media.api.MediaPlayerRepository
import com.gab.core_media.api.TracksListRepository
import com.gab.core_media.impl.MediaRepository
import dagger.Binds
import dagger.Module

@Module
public interface CoreMediaModule{

    @Binds
    @MediaScope
    public fun bindMediaPlayerRepository(impl: MediaRepository): MediaPlayerRepository

    @Binds
    @MediaScope
    public fun bindTracksListRepository(impl: MediaRepository): TracksListRepository

}