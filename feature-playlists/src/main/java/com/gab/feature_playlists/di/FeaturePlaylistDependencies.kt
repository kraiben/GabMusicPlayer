package com.gab.feature_playlists.di

import androidx.lifecycle.ViewModelProvider
import com.gab.feature_api_options_menus.PlaylistScreenMenuHolder

public interface FeaturePlaylistDependencies {
    public val viewModelFactory: ViewModelProvider.Factory
    public val playlistScreenMenuHolder: PlaylistScreenMenuHolder
}