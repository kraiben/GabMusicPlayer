package com.gab.gabsmusicplayer.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.gab.core_music_loading.impl.di.CoreMusicLoadingDependencies
import com.gab.core_settings.impl.di.CoreSettingsDependencies
import com.gab.gabsmusicplayer.ui.general.MainActivity
import com.gab.model_module.di.UseCasesDependencies
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        AppDataModule::class,
        ViewModelModule::class,
        AppUseCasesModule::class
    ]
)
interface ApplicationComponent :
    CoreSettingsDependencies,
    CoreMusicLoadingDependencies,
    UseCasesDependencies {

    override val dataStore: DataStore<Preferences>

    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
        ): ApplicationComponent
    }

}