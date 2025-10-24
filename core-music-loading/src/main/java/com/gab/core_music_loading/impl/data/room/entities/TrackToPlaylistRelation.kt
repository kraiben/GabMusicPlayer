package com.gab.core_music_loading.impl.data.room.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE


@Entity(
    tableName = "track_to_playlist_relations",
    primaryKeys = ["playlistId", "trackId"],
    foreignKeys = [
        ForeignKey(
            entity = TrackInfoEntity::class,
            parentColumns = ["id"],
            childColumns = ["trackId"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = PlaylistInfoEntity::class,
            parentColumns = ["id"],
            childColumns = ["playlistId"],
            onDelete = CASCADE
        )
    ]
)
public data class TrackToPlaylistRelation(
    val playlistId: Long,
    val trackId: Long
)