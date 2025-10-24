package com.gab.core_music_loading.impl.data.room.entities

import androidx.room.Entity

@Entity(
    tableName = "tracks",
    primaryKeys = [
        "id", "title", "artist"
    ]
)
public data class TrackInfoEntity(
    val id: Long,
    val title: String,
    val artist: String,
    val hasAlbumArtUri: Boolean,
)
