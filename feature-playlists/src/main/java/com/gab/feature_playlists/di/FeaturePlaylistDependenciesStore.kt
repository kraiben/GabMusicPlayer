package com.gab.feature_playlists.di

import kotlin.properties.Delegates.notNull

public object FeaturePlaylistDependenciesStore: FeaturePlaylistDependenciesProvider{

    override var deps: FeaturePlaylistDependencies by notNull()

}