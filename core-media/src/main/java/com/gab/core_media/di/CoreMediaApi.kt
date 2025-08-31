package com.gab.core_media.di

import com.gab.core_media.api.MediaPlayerRepository
import com.gab.core_media.api.TracksListRepository

public interface CoreMediaApi {
    public fun getTracksListRepository(): TracksListRepository
    public fun getMediaPlayerRepository(): MediaPlayerRepository
}