package com.gab.model_media_usecases.di

import com.gab.core_media.api.MediaPlayerRepository
import com.gab.core_media.api.TracksListRepository

public interface ModelMediaUseCasesDependencies {

    public val mediaPlayerRepository: MediaPlayerRepository
    public val tracksListRepository: TracksListRepository

}