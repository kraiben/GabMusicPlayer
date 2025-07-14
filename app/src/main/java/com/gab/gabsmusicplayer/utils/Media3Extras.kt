package com.gab.gabsmusicplayer.utils

import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.session.MediaController
import com.gab.gabsmusicplayer.domain.models.TrackInfoModel
import kotlin.random.Random

fun MediaController.setPlaylist(
    tracks: List<TrackInfoModel>,
    startIndex: Int? = null,
    isShuffled: Boolean = false,
) {
    val mediaItems = tracks.map {
        it.toMediaItem()
    }
    val startFrom = startIndex?.let {
        startIndex
    } ?: Random.nextInt(tracks.size)
    this.setMediaItems(mediaItems)
    this.seekTo(startFrom, 0L)
    this.prepare()
    this.shuffleModeEnabled = isShuffled
    this.play()
}

fun MediaController.moveTrackToStartOfQuery(track: TrackInfoModel) {
    GAB_CHECK(this.shuffleModeEnabled)
    val curIndex = this.currentMediaItemIndex
    val size = this.mediaItemCount
    for (i in 0 until size) {
        if (this.getMediaItemAt(i).mediaId == track.id.toString()) {
            if ((i == curIndex) || (i == (curIndex + 1) % size)) return
            if (i > curIndex) this.moveMediaItem(i, this.nextMediaItemIndex)
            else this.moveMediaItem(i, curIndex)
            return
        }
    }
    this.addMediaItem(
        this.nextMediaItemIndex, track.toMediaItem()
    )
    GAB_CHECK("Track Added")
}

fun TrackInfoModel.toMediaItem(): MediaItem = MediaItem.Builder()
    .setUri(this.path)
    .setMediaId(this.id.toString())
    .setMediaMetadata(
        MediaMetadata.Builder()
            .setTitle(this.title)
            .setArtworkUri(this.albumArtUri)
            .setArtist(this.artist)
            .setDurationMs(this.duration)
            .setArtworkData(null, this.albumArtUriIsNullPatchId)
            .build()
    ).setMediaId(this.id.toString())
    .build()