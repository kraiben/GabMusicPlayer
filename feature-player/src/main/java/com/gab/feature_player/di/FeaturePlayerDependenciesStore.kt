package com.gab.feature_player.di

import kotlin.properties.Delegates.notNull

public object FeaturePlayerDependenciesStore: FeaturePlayerDependenciesProvider{

    override var deps: FeaturePlayerDependencies by notNull()

}