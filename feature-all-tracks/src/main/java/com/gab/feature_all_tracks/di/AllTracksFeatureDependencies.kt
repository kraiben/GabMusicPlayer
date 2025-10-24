package com.gab.feature_all_tracks.di

import androidx.lifecycle.ViewModelProvider
import com.gab.feature_api_options_menus.AllTracksScreenOptionsMenuHolder

public interface AllTracksFeatureDependencies {
    public val viewModelFactory: ViewModelProvider.Factory
    public val trackOptionsMenu: AllTracksScreenOptionsMenuHolder
}