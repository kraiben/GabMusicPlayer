package com.gab.gabsmusicplayer.ui

import android.app.Application
import com.gab.core_music_loading.impl.di.CoreMusicLoadingDependenciesStore
import com.gab.core_settings.impl.di.CoreSettingsDependenciesStore
import com.gab.gabsmusicplayer.di.ApplicationComponent
import com.gab.gabsmusicplayer.di.DaggerApplicationComponent
import com.gab.model_module.di.UseCasesDependenciesStore

class MusicApplication : Application() {

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        CoreMusicLoadingDependenciesStore.deps = component
        CoreSettingsDependenciesStore.deps = component
        UseCasesDependenciesStore.deps = component
    }
}