package com.gab.gabsmusicplayer.di

import android.content.ContentResolver
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.gab.gabsmusicplayer.data.repository.musicLoading.MusicLoadingRepositoryImpl
import com.gab.gabsmusicplayer.data.repository.settings.SettingsRepositoryImpl
import com.gab.gabsmusicplayer.domain.repository.MusicLoadingRepository
import com.gab.gabsmusicplayer.domain.repository.SettingsRepository
import com.gab.gabsmusicplayer.utils.dataStore
import dagger.Binds
import dagger.Module
import dagger.Provides
import java.io.File


@Module
interface DataModule {

//    @ApplicationScope
//    @Binds
//    fun bindMainRepository(repositoryImpl: MusicRepositoryImpl): MusicRepository

    @ApplicationScope
    @Binds
    fun bindMusicLoadingRepository(repositoryImpl: MusicLoadingRepositoryImpl): MusicLoadingRepository

    @ApplicationScope
    @Binds
    fun bindSettingsRepository(repositoryImpl: SettingsRepositoryImpl): SettingsRepository

    companion object {

        @ApplicationScope
        @Provides
        fun provideContentResolver(context: Context): ContentResolver {
            return context.contentResolver
        }

        @ApplicationScope
        @Provides
        fun provideFileDirectory(context: Context): File {
            return context.filesDir
        }

        @ApplicationScope
        @Provides
        fun provideDataStore(context: Context): DataStore<Preferences> {
            return context.dataStore
        }
    }

}