package com.gab.navigation_module.di

import kotlin.properties.Delegates.notNull

public object NavigationModuleDependenciesStore: NavigationModuleDependenciesProvider{

    override var deps: NavigationModuleDependencies by notNull()

}