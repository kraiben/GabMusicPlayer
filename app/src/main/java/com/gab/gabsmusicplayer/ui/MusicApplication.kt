package com.gab.gabsmusicplayer.ui

import android.app.Application
import com.gab.core_media.di.CoreMediaDependenciesStore
import com.gab.core_music_loading.impl.di.CoreMusicLoadingDependenciesStore
import com.gab.core_settings.impl.di.CoreSettingsDependenciesStore
import com.gab.feature_all_tracks.di.AllTracksFeatureDependenciesStore
import com.gab.feature_options_menus.di.TrackOptionsMenuDependenciesStore
import com.gab.feature_playlists.di.FeaturePlaylistDependenciesStore
import com.gab.gabsmusicplayer.di.ApplicationComponent
import com.gab.gabsmusicplayer.di.DaggerApplicationComponent
import com.gab.model_media_usecases.di.ModelMediaUseCasesDependenciesStore
import com.gab.model_module.di.DbUseCasesDependenciesStore

class MusicApplication : Application() {

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        CoreMusicLoadingDependenciesStore.deps = component
        CoreSettingsDependenciesStore.deps = component
        DbUseCasesDependenciesStore.deps = component
        CoreMediaDependenciesStore.deps = component
        ModelMediaUseCasesDependenciesStore.deps = component
        AllTracksFeatureDependenciesStore.deps = component
        TrackOptionsMenuDependenciesStore.deps = component
        FeaturePlaylistDependenciesStore.deps = component
    }
}