package com.gab.model_module.di

import androidx.annotation.RestrictTo

public interface UseCasesDependenciesProvider {

    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    public val deps: UseCasesDependencies

    public companion object : UseCasesDependenciesProvider by UseCasesDependenciesStore

}