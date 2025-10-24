package com.gab.feature_playlists.di

import com.gab.feature_api_playlists.PlaylistFeatureHolder

public interface FeatureComponentApi {
    public fun getHolder(): PlaylistFeatureHolder
}