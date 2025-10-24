package com.gab.core_music_loading.impl.data.room.entities.database

import android.net.Uri
import com.gab.core_music_loading.impl.data.room.entities.PlaylistInfoEntity
import com.gab.core_music_loading.impl.data.room.entities.TrackInfoEntity
import com.gab.music_entities_module.PlaylistInfoModel
import com.gab.music_entities_module.TrackInfoModel
import javax.inject.Inject

internal class DatabaseMapper @Inject constructor() {
    internal fun TrackInfoModel.toEntity() = TrackInfoEntity(
        id = id,
        title = title,
        artist = artist,
        hasAlbumArtUri = albumArtUri != Uri.EMPTY
    )
    internal fun PlaylistInfoModel.toEntity() =
        PlaylistInfoEntity(
            id = id,
            title = title,
            coverUri = coverUri.toString(),
            createdAt = createdAt.time
        )
}