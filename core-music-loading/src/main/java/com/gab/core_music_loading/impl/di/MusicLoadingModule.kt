package com.gab.core_music_loading.impl.di

import android.content.Context
import androidx.room.Room
import com.gab.core_music_loading.api.data.MusicLoadingRepository
import com.gab.core_music_loading.impl.data._MusicLoadingRepositoryImpl
import com.gab.core_music_loading.impl.data.room.entities.database.MusicDao
import com.gab.core_music_loading.impl.data.room.entities.database.MusicDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
internal interface MusicLoadingModule {
    @MusicLoadingScope
    @Binds
    fun bindMusicLoadingRepository(impl: _MusicLoadingRepositoryImpl): MusicLoadingRepository

    companion object {
        @Provides
        fun provideDatabase(context: Context): MusicDatabase {
            return Room.databaseBuilder(context, MusicDatabase::class.java, name = "music_database.db")
                .build()
        }

        @Provides
        fun provideRecipesDao(recipesDataBase: MusicDatabase): MusicDao {
            return recipesDataBase.musicDao()
        }
    }
}