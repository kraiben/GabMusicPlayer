package com.gab.navigation_module.di

import androidx.lifecycle.ViewModelProvider
import com.gab.feature_api_all_tracks.AllTTracksFeatureHolder
import com.gab.feature_api_player.FeaturePlayerHolder
import com.gab.feature_api_playlists.PlaylistFeatureHolder
import com.gab.theme_api_module.ThemeHolder

public interface NavigationModuleDependencies {
    public val viewModelFactory: ViewModelProvider.Factory
    public val themeHolder: ThemeHolder
    public val allTracksFeatureHolder: AllTTracksFeatureHolder
    public val playlistFeatureHolder: PlaylistFeatureHolder
    public val featurePlayerHolder: FeaturePlayerHolder
}