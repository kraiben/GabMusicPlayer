package com.gab.gabsmusicplayer.di

import android.content.ContentResolver
import android.content.Context
import com.gab.gabsmusicplayer.data.repository.MusicRepositoryImpl
import com.gab.gabsmusicplayer.domain.repository.MusicRepository
import dagger.Binds
import dagger.Module
import dagger.Provides


@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindMainRepositor(repositoryImpl: MusicRepositoryImpl): MusicRepository

    companion object {

        @ApplicationScope
        @Provides
        fun provideContentResolver(context: Context): ContentResolver {
            return context.contentResolver
        }
//
//        @ApplicationScope
//        @Provides
//        fun provideDataStore(context: Context): DataStore<Preferences> {
//            return PreferenceDataStoreFactory.create(
//                produceFile = {
//                    context.preferencesDataStoreFile("music_settings")
//                }
//            )
//        }
    }

}