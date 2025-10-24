package com.gab.feature_player.di

import dagger.Component

@FeaturePlayerScope
@Component(
    modules = [FeaturePlayerModule::class],
    dependencies = [FeaturePlayerDependencies::class]
)
public interface FeaturePlayerComponent: FeaturePlayerComponentApi {
    @Component.Factory
    public interface Factory {
        public fun create(deps: FeaturePlayerDependencies): FeaturePlayerComponent
    }
    public companion object {
        @Volatile
        private var instance: FeaturePlayerComponent? = null

        public fun get(): FeaturePlayerComponent  {
            if (instance == null) {
                synchronized(FeaturePlayerComponent::class) {
                    if (instance == null) {
                        instance = DaggerFeaturePlayerComponent.factory().create(
                            FeaturePlayerDependenciesProvider.deps)
                    }
                }
            }
            return instance!!
        }
    }
}