package com.gab.gabsmusicplayer.utils

import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.session.MediaController
import com.gab.gabsmusicplayer.domain.models.TrackInfoModel

fun MediaController.setPlaylist(tracks: List<TrackInfoModel>, startIndex: Int = 0) {
    val mediaItems = tracks.map {
        MediaItem.Builder()
            .setUri(it.path)
            .setMediaId(it.id.toString())
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setTitle(it.title)
                    .setArtworkUri(it.albumArtUri)
                    .setArtist(it.artist)
                    .setDurationMs(it.duration)
                    .build()
            )
            .build()
    }
    this.setMediaItems(mediaItems)
    this.seekTo(startIndex, 0L)
    this.prepare()
    this.play()
}