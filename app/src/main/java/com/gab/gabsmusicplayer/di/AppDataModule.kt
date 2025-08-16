package com.gab.gabsmusicplayer.di

import android.content.ContentResolver
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.gab.core_music_loading.api.data.MusicLoadingRepository
import com.gab.core_music_loading.impl.di.CoreMusicLoadingComponent
import com.gab.core_settings.api.data.SettingsRepository
import com.gab.core_settings.impl.di.CoreSettingsComponent
import com.gab.gabsmusicplayer.utils.dataStore
import dagger.Module
import dagger.Provides
import java.io.File


@Module
interface AppDataModule {

    companion object {

        @Provides
        fun provideContentResolver(context: Context): ContentResolver {
            return context.contentResolver
        }

        @Provides
        fun provideFileDirectory(context: Context): File {
            return context.filesDir
        }

        @Provides
        fun provideDataStore(context: Context): DataStore<Preferences> {
            return context.dataStore
        }

        @ApplicationScope
        @Provides
        fun provideMusicLoadingRepository(): MusicLoadingRepository = CoreMusicLoadingComponent.get().musicLoadingRepository()

        @ApplicationScope
        @Provides
        fun provideSettingsRepository(): SettingsRepository = CoreSettingsComponent.get().settingsRepository()
    }

}