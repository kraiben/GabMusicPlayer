package com.gab.feature_playlists.di

import com.gab.feature_playlists.holder.PlaylistFeatureHolder

public interface FeatureComponentApi {
    public fun getHolder(): PlaylistFeatureHolder
}