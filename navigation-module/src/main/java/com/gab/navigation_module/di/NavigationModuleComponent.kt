package com.gab.navigation_module.di

import dagger.Component

@NavigationModuleScope
@Component(
    modules = [NavigationModuleModule::class],
    dependencies = [NavigationModuleDependencies::class]
)
public interface NavigationModuleComponent: NavigationModuleComponentApi {
    @Component.Factory
    public interface Factory {
        public fun create(deps: NavigationModuleDependencies): NavigationModuleComponent
    }
    public companion object {
        @Volatile
        private var instance: NavigationModuleComponent? = null

        public fun get(): NavigationModuleComponent  {
            if (instance == null) {
                synchronized(NavigationModuleComponent::class) {
                    if (instance == null) {
                        instance = DaggerNavigationModuleComponent.factory().create(
                            NavigationModuleDependenciesProvider.deps)
                    }
                }
            }
            return instance!!
        }
    }
}