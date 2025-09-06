package com.gab.feature_playlists.di

import androidx.annotation.RestrictTo

internal interface FeaturePlaylistDependenciesProvider {
    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val deps: FeaturePlaylistDependencies

    companion object: FeaturePlaylistDependenciesProvider by FeaturePlaylistDependenciesStore
}