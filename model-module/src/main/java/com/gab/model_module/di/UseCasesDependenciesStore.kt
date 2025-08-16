package com.gab.model_module.di

import kotlin.properties.Delegates.notNull

public object UseCasesDependenciesStore : UseCasesDependenciesProvider {

    public override var deps: UseCasesDependencies by notNull()

}