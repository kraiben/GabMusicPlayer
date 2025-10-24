package com.gab.core_music_loading.impl.data.room.entities.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gab.core_music_loading.impl.data.room.entities.PlaylistInfoEntity
import com.gab.core_music_loading.impl.data.room.entities.PlaylistWithTracks
import com.gab.core_music_loading.impl.data.room.entities.TrackInfoEntity
import com.gab.core_music_loading.impl.data.room.entities.TrackToPlaylistRelation

@Database(
    entities = [
        PlaylistInfoEntity::class,
        PlaylistWithTracks::class,
        TrackInfoEntity::class,
        TrackToPlaylistRelation::class,
    ],
    version = 1,
    exportSchema = false
)
public abstract class MusicDatabase : RoomDatabase() {
    public abstract fun musicDao(): MusicDao
}