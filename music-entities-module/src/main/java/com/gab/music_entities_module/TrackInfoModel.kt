package com.gab.music_entities_module

import android.net.Uri
import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import java.util.Date

@Keep
@Immutable
public data class TrackInfoModel(
    val id: Long,
    val title: String,
    val artist: String,
    val path: String,
    val duration: Long,
    val albumArtUri: Uri,
    val albumArtUriIsNullPatchId: Int,
    val dateAdded: Date = Date(),
) {
    public companion object {

        public val EMPTY: TrackInfoModel = TrackInfoModel(
            id = -1,
            title = "",
            artist = "",
            path = "",
            duration = 0L,
            albumArtUri = Uri.EMPTY,
            albumArtUriIsNullPatchId = 0
        )
    }
}