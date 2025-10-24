package com.gab.core_music_loading.impl.data.room.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation


public data class PlaylistWithTracks(
    @Embedded
    val playlist: PlaylistInfoEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = TrackToPlaylistRelation::class,
            parentColumn = "playlistId",
            entityColumn = "trackId"
        )
    )
    val tracks: List<TrackInfoEntity>
)