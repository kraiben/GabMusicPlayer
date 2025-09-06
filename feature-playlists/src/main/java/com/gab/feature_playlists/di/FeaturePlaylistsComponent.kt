package com.gab.feature_playlists.di

import dagger.Component

@FeaturePlaylistsScope
@Component(
    modules = [FeaturePlaylistsModule::class],
    dependencies = [FeaturePlaylistDependencies::class]
)
public interface FeaturePlaylistsComponent: FeatureComponentApi {
    @Component.Factory
    public interface Factory {
        public fun create(deps: FeaturePlaylistDependencies): FeaturePlaylistsComponent
    }
    public companion object {
        @Volatile
        private var instance: FeaturePlaylistsComponent? = null

        public fun get(): FeaturePlaylistsComponent  {
            if (instance == null) {
                synchronized(FeaturePlaylistsComponent::class) {
                    if (instance == null) {
                        instance = DaggerFeaturePlaylistsComponent.factory().create(
                            FeaturePlaylistDependenciesProvider.deps)
                    }
                }
            }
            return instance!!
        }
    }
}