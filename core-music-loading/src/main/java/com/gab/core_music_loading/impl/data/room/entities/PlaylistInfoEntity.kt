package com.gab.core_music_loading.impl.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "playlists_short"
)
public data class PlaylistInfoEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val coverUri: String,
    val createdAt: Long,
)
