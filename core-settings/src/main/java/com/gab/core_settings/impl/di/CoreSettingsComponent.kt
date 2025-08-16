package com.gab.core_settings.impl.di

import androidx.annotation.RestrictTo
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.gab.core_settings.api.di.SettingsApi
import dagger.Component
import kotlin.properties.Delegates.notNull

@Component(modules = [CoreSettingsModule::class], dependencies = [CoreSettingsDependencies::class])
@CoreSettingsScope
public abstract class CoreSettingsComponent: SettingsApi {

    @Component.Factory
    public interface Factory {
        public fun create(
            deps: CoreSettingsDependencies
        ): CoreSettingsComponent
    }

    public companion object {

        private var instance: CoreSettingsComponent? = null

        public fun get(): CoreSettingsComponent {
            if (instance == null) {
                synchronized(CoreSettingsComponent::class.java) {
                    if (instance == null) {
                        instance = DaggerCoreSettingsComponent.factory(
                            ).create(CoreSettingsDependenciesProvider.deps)
                    }
                }
            }
            return instance!!
        }
    }
}

public interface CoreSettingsDependencies {
    public val dataStore: DataStore<Preferences>
}
public interface CoreSettingsDependenciesProvider {

    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    public val deps: CoreSettingsDependencies

    public companion object: CoreSettingsDependenciesProvider by CoreSettingsDependenciesStore
}

public object CoreSettingsDependenciesStore: CoreSettingsDependenciesProvider {
    override var deps: CoreSettingsDependencies by notNull()
}