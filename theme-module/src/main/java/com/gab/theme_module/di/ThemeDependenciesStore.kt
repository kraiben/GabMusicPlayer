package com.gab.theme_module.di

import kotlin.properties.Delegates.notNull

public object ThemeDependenciesStore: ThemeDependenciesProvider{

    override var deps: ThemeDependencies by notNull()

}