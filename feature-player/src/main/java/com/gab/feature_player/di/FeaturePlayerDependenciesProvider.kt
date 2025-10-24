package com.gab.feature_player.di

import androidx.annotation.RestrictTo

internal interface FeaturePlayerDependenciesProvider {
    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val deps: FeaturePlayerDependencies

    companion object: FeaturePlayerDependenciesProvider by FeaturePlayerDependenciesStore
}