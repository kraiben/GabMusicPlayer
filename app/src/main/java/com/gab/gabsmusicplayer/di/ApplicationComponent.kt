package com.gab.gabsmusicplayer.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.gab.core_media.di.CoreMediaDependencies
import com.gab.core_music_loading.impl.di.CoreMusicLoadingDependencies
import com.gab.core_settings.impl.di.CoreSettingsDependencies
import com.gab.feature_all_tracks.di.AllTracksFeatureDependencies
import com.gab.feature_options_menus.di.TrackOptionsMenuDependencies
import com.gab.feature_playlists.di.FeaturePlaylistDependencies
import com.gab.gabsmusicplayer.di.view_model.ViewModelModule
import com.gab.gabsmusicplayer.ui.general.MainActivity
import com.gab.model_media_usecases.di.ModelMediaUseCasesDependencies
import com.gab.model_module.di.UseCasesDependencies
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        AppDataModule::class,
        ViewModelModule::class,
        DbUseCasesModule::class,
        MediaUseCasesModule::class,
        FeaturesModule::class,
    ]
)
interface ApplicationComponent :
    CoreSettingsDependencies,
    CoreMusicLoadingDependencies,
    UseCasesDependencies,
    CoreMediaDependencies,
    ModelMediaUseCasesDependencies,
    AllTracksFeatureDependencies,
    TrackOptionsMenuDependencies,
    FeaturePlaylistDependencies
{

    override val dataStore: DataStore<Preferences>

    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
        ): ApplicationComponent
    }

}