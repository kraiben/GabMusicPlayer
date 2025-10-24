package com.gab.navigation_module.di

import androidx.annotation.RestrictTo

internal interface NavigationModuleDependenciesProvider {
    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val deps: NavigationModuleDependencies

    companion object: NavigationModuleDependenciesProvider by NavigationModuleDependenciesStore
}