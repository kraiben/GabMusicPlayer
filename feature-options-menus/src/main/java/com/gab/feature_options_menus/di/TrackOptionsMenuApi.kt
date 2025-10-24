package com.gab.feature_options_menus.di

import com.gab.feature_api_options_menus.AllTracksScreenOptionsMenuHolder
import com.gab.feature_api_options_menus.PlaylistScreenMenuHolder

public interface TrackOptionsMenuApi {
    public fun getPlaylistScreenMenuHolder(): PlaylistScreenMenuHolder
    public fun getAllTracksScreenOptionsMenuHolder(): AllTracksScreenOptionsMenuHolder
}