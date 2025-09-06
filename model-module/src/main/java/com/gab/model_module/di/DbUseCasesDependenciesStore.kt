package com.gab.model_module.di

import kotlin.properties.Delegates.notNull

public object DbUseCasesDependenciesStore : DbUseCasesDependenciesProvider {

    public override var deps: UseCasesDependencies by notNull()

}