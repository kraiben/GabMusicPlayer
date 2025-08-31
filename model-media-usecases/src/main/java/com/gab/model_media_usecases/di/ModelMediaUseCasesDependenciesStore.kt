package com.gab.model_media_usecases.di

import kotlin.properties.Delegates.notNull

public object ModelMediaUseCasesDependenciesStore: ModelMediaUseCasesDependenciesProvider {
    override var deps: ModelMediaUseCasesDependencies by notNull()
}