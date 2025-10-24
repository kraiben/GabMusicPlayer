package com.gab.theme_module.di

import androidx.annotation.RestrictTo

internal interface ThemeDependenciesProvider {
    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val deps: ThemeDependencies

    companion object: ThemeDependenciesProvider by ThemeDependenciesStore
}