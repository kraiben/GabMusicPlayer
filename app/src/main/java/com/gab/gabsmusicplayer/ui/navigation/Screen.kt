package com.gab.gabsmusicplayer.ui.navigation

import android.net.Uri
import androidx.media3.common.MediaItem
import com.google.gson.Gson

sealed class Screen(val route: String) {

    data object AllTracksScreen: Screen(ALL_TRACKS_ROUTE)

    data object AllPlaylistsScreen: Screen(ALL_PLAYLISTS_ROUTE)

    data object PlaylistScreen: Screen(ALL_PLAYLISTS_ROUTE)

    data object AudioPlayerScreen: Screen(AUDIO_PLAYER_ROUTE) {

//        private const val ROUTE_FOR_ARGS = "audio_player_screen"
//
//        fun getRouteWithArgs(mediaItem: MediaItem): String {
//            val mediaItemJson = Gson().toJson(mediaItem).encode()
//            return "${ROUTE_FOR_ARGS}/${mediaItemJson}"
//        }
    }

    companion object {

        const val ALL_TRACKS_ROUTE = "all_tracks_screen"

//        const val KEY_PLAYER = "audio_player"
        const val AUDIO_PLAYER_ROUTE = "audio_player_screen"

        const val ALL_PLAYLISTS_ROUTE = "all_playlists_screen"
        const val PLAYLIST_ROUTE = "playlist_screen"
    }
}
fun String.encode(): String {
    return Uri.encode(this)
}