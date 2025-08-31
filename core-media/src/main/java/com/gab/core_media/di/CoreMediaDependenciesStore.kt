package com.gab.core_media.di

import kotlin.properties.Delegates.notNull

public object CoreMediaDependenciesStore: CoreMediaDependenciesProvider {

    public override var deps: CoreMediaDependencies by notNull()

}