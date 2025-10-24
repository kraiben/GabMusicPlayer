package com.gab.theme_module.di

import dagger.Component

@ThemeScope
@Component(
    modules = [ThemeModule::class],
    dependencies = [ThemeDependencies::class]
)
public interface ThemeComponent: ThemeComponentApi {
    @Component.Factory
    public interface Factory {
        public fun create(deps: ThemeDependencies): ThemeComponent
    }
    public companion object {
        @Volatile
        private var instance: ThemeComponent? = null

        public fun get(): ThemeComponent  {
            if (instance == null) {
                synchronized(ThemeComponent::class) {
                    if (instance == null) {
                        instance = DaggerThemeComponent.factory().create(
                            ThemeDependenciesProvider.deps)
                    }
                }
            }
            return instance!!
        }
    }
}