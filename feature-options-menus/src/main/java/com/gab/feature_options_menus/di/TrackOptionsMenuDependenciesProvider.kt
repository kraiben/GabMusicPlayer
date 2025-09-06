package com.gab.feature_options_menus.di

import androidx.annotation.RestrictTo

internal interface TrackOptionsMenuDependenciesProvider {
    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val deps: TrackOptionsMenuDependencies

    companion object: TrackOptionsMenuDependenciesProvider by TrackOptionsMenuDependenciesStore
}