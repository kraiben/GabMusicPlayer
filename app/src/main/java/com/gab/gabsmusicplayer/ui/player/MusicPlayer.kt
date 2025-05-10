package com.gab.gabsmusicplayer.ui.player

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.gab.gabsmusicplayer.domain.models.TrackInfoModel

class MusicPlayer (
    private val exoPlayer: ExoPlayer,
) {

    private var isPlaying: Boolean = false

    private var player: ExoPlayer = exoPlayer.apply {
        addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                when (state) {
                    Player.STATE_ENDED -> {
                        nextTrack()
                    }

                    else -> {}
                }
            }
        })
    }

    fun startPlaylist(tracks: List<TrackInfoModel>) {
        player.let { player ->
            val mediaItems = tracks.map {
                MediaItem.fromUri(it.path)
            }
            player.setMediaItems(mediaItems, true)
            player.prepare()
            player.play()
        }
    }

    fun nextTrack() {
        player.seekToNext()
    }
    fun previousTrack() {
        player.seekToPrevious()
    }

    fun pause() {
        player.pause()
    }

    fun stop() {
        player.stop()
    }

}