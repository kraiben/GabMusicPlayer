package com.gab.core_media.di

import androidx.annotation.RestrictTo

public interface CoreMediaDependenciesProvider {

    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    public val deps: CoreMediaDependencies

    public companion object: CoreMediaDependenciesProvider by CoreMediaDependenciesStore
}