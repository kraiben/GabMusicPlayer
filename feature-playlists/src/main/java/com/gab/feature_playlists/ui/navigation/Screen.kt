package com.gab.feature_playlists.ui.navigation

import android.net.Uri
import com.gab.music_entities_module.PlaylistInfoModel
import com.google.gson.Gson

internal sealed class Screen(internal val route: String) {

    data object Playlist : Screen(PLAYLIST)

    data object AllPlaylistsScreen : Screen(ROUTE_ALL_PLAYLISTS_SCREEN)

    data object PlaylistEditOrAddScreen : Screen(ROUTE_PLAYLIST_EDIT_OR_ADD_SCREEN) {
        private const val ROUTE_FOR_ARGS = "playlist_edit_or_ad"

        fun getRouteWithArgs(playlist: PlaylistInfoModel, screenMode: PlaylistChangesScreenMode):
                String {
            val playlistJson = playlist.toJson().encode()
            val screenModeJson = Gson().toJson(screenMode).encode()
            return "$ROUTE_FOR_ARGS/${playlistJson}/${screenModeJson}"
        }
    }

    data object SinglePlaylistScreen : Screen(ROUTE_SINGLE_PLAYLIST_SCREEN) {
        private const val ROUTE_FOR_ARGS = "single_playlist_screen"

        fun getRouteWithArgs(playlistId: Long): String {
            return "$ROUTE_FOR_ARGS/${playlistId}"
        }
    }

    companion object {


        const val PLAYLIST = "playlist_base"
        const val KEY_PLAYLIST = "playlist_info_model"
        const val ROUTE_ALL_PLAYLISTS_SCREEN = "all_playlists_screen"
        const val ROUTE_SINGLE_PLAYLIST_SCREEN = "single_playlist_screen/{$KEY_PLAYLIST}"
        const val KEY_PLAYLIST_EDIT_OR_ADD = "playlist_e_d"
        const val KEY_PLAYLIST_EDIT_OR_ADD_SCREEN_MODE = "playlist_e_d_screen_mode"
        const val ROUTE_PLAYLIST_EDIT_OR_ADD_SCREEN =
            "playlist_edit_or_ad/{$KEY_PLAYLIST_EDIT_OR_ADD}/{$KEY_PLAYLIST_EDIT_OR_ADD_SCREEN_MODE}"
    }
}

private fun String.encode(): String {
    return Uri.encode(this)
}