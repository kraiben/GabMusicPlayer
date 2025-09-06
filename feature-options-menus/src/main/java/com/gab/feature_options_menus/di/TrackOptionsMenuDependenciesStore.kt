package com.gab.feature_options_menus.di

import kotlin.properties.Delegates.notNull

public object TrackOptionsMenuDependenciesStore: TrackOptionsMenuDependenciesProvider {
    override var deps: TrackOptionsMenuDependencies by notNull()
}