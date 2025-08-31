package com.gab.core_media.di

import dagger.Component

@Component(modules = [CoreMediaModule::class], dependencies = [CoreMediaDependencies::class])
@MediaScope
public abstract class CoreMediaComponent: CoreMediaApi {

    @Component.Factory
    public interface Factory {
        public fun create(
            deps: CoreMediaDependencies
        ): CoreMediaComponent
    }

    public companion object {
        private var instance: CoreMediaComponent? = null

        public fun get(): CoreMediaComponent {
            if (instance == null){
                synchronized(CoreMediaComponent::class) {
                    if (instance == null) {
                        instance = DaggerCoreMediaComponent.factory().create(
                            CoreMediaDependenciesProvider.deps)
                    }
                }
            }
            return instance!!
        }
    }
}