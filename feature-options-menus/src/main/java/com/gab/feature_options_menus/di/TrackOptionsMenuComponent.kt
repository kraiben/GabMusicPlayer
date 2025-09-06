package com.gab.feature_options_menus.di

import dagger.Component

@Component(
    dependencies = [TrackOptionsMenuDependencies::class],
    modules = [TrackOptionsMenuModule::class]
)
@TrackOptionsMenuScope
public interface TrackOptionsMenuComponent : TrackOptionsMenuApi {

    @Component.Factory
    public interface Factory {

        public fun create(deps: TrackOptionsMenuDependencies): TrackOptionsMenuComponent

    }

    public companion object {

        private var instance: TrackOptionsMenuComponent? = null

        public fun get(): TrackOptionsMenuComponent {
            if (instance == null) {
                synchronized(TrackOptionsMenuComponent::class) {
                    if (instance == null) {
                        instance = DaggerTrackOptionsMenuComponent.factory().create(
                            TrackOptionsMenuDependenciesProvider.deps
                        )
                    }
                }
            }
            return instance!!
        }
    }
}