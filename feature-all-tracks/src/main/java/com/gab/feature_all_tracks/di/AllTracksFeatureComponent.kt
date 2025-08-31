package com.gab.feature_all_tracks.di

import com.gab.feature_all_tracks.holder.AllTracksFeatureHolderImpl
import dagger.Component

@AllTracksFeatureScope
@Component(dependencies = [AllTracksFeatureDependencies::class], modules = [AllTracksFeatureModule::class])
public interface AllTracksFeatureComponent: AllTracksFeatureComponentApi {
    public fun inject(holder: AllTracksFeatureHolderImpl)
    @Component.Factory
    public interface Factory {
        public fun create(deps: AllTracksFeatureDependencies): AllTracksFeatureComponent
    }
    public companion object {
        @Volatile
        private var instance: AllTracksFeatureComponent? = null

        public fun get(): AllTracksFeatureComponent  {
            if (instance == null) {
                synchronized(AllTracksFeatureComponent::class) {
                    if (instance == null) {
                        instance = DaggerAllTracksFeatureComponent.factory().create(
                            AllTracksFeatureDependenciesProvider.deps)
                    }
                }
            }
            return instance!!
        }
    }
}
