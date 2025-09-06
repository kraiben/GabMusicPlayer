package com.gab.feature_all_tracks.di

import kotlin.properties.Delegates.notNull

public object AllTracksFeatureDependenciesStore: AllTracksFeatureDependenciesProvider {
    override var deps: AllTracksFeatureDependencies by notNull()
}