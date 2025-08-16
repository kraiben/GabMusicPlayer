package com.gab.model_module.di

import androidx.annotation.RestrictTo
import dagger.Component
import kotlin.properties.Delegates.notNull

@Component(
    modules = [UseCasesModule::class],
    dependencies = [UseCasesDependencies::class]
)
@UseCasesScope
public abstract class UseCasesComponent : UseCasesApi {

    @Component.Factory
    public interface Factory {
        public fun create(
            deps: UseCasesDependencies,
        ): UseCasesComponent
    }

    public companion object {
        private var instance: UseCasesComponent? = null

        public fun get(): UseCasesComponent {
            if (instance == null) {
                synchronized(UseCasesComponent::class) {
                    if (instance == null) {
                        instance = DaggerUseCasesComponent.factory().create(
                            UseCasesDependenciesProvider.deps
                        )
                    }
                }
            }
            return instance!!
        }
    }
}



