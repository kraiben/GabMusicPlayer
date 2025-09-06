package com.gab.feature_all_tracks.di

import androidx.annotation.RestrictTo

public interface AllTracksFeatureDependenciesProvider {

    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    public val deps: AllTracksFeatureDependencies

    public companion object: AllTracksFeatureDependenciesProvider by AllTracksFeatureDependenciesStore

}