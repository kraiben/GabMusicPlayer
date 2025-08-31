package com.gab.model_media_usecases.di

import androidx.annotation.RestrictTo

public interface ModelMediaUseCasesDependenciesProvider {

    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    public val deps: ModelMediaUseCasesDependencies

    public companion object :
        ModelMediaUseCasesDependenciesProvider by ModelMediaUseCasesDependenciesStore
}