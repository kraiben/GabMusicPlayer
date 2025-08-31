package com.gab.model_module.di

import androidx.annotation.RestrictTo

public interface DbUseCasesDependenciesProvider {

    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    public val deps: UseCasesDependencies

    public companion object : DbUseCasesDependenciesProvider by DbUseCasesDependenciesStore

}