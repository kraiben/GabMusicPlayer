package com.gab.gabsmusicplayer.domain.models

import android.net.Uri
import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import java.util.Date

@Immutable
@Keep
data class TrackInfoModel(
    val id: Long,
    val title: String,
    val artist: String,
    val path: String,
    val duration: Long,
    val albumArtUri: Uri,
    val albumArtUriIsNullPatchId: Int,
    val dateAdded: Date = Date(),
)