package com.gab.model_module.di

import dagger.Component

@Component(
    modules = [UseCasesModule::class],
    dependencies = [UseCasesDependencies::class]
)
@UseCasesScope
public abstract class DbUseCasesComponent : UseCasesApi {

    @Component.Factory
    public interface Factory {
        public fun create(
            deps: UseCasesDependencies,
        ): DbUseCasesComponent
    }

    public companion object {
        private var instance: DbUseCasesComponent? = null

        public fun get(): DbUseCasesComponent {
            if (instance == null) {
                synchronized(DbUseCasesComponent::class) {
                    if (instance == null) {
                        instance = DaggerDbUseCasesComponent.factory().create(
                            DbUseCasesDependenciesProvider.deps
                        )
                    }
                }
            }
            return instance!!
        }
    }
}



