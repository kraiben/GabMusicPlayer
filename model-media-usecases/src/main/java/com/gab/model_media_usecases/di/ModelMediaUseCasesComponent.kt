package com.gab.model_media_usecases.di

import dagger.Component

@Component(modules = [ModelMediaUseCasesModule::class], dependencies = [ModelMediaUseCasesDependencies::class])
public interface ModelMediaUseCasesComponent: ModelMediaUseCasesApi {

    @Component.Factory
    public interface Factory {
        public fun create(
            deps: ModelMediaUseCasesDependencies
        ): ModelMediaUseCasesComponent
    }

    public companion object {

        private var instance: ModelMediaUseCasesComponent? = null

        public fun get():ModelMediaUseCasesComponent  {
            if (instance == null) {
                synchronized(ModelMediaUseCasesComponent::class) {
                    if (instance == null) {
                        instance = DaggerModelMediaUseCasesComponent.factory().create(
                            ModelMediaUseCasesDependenciesProvider.deps)
                    }
                }
            }
            return instance!!
        }

    }

}