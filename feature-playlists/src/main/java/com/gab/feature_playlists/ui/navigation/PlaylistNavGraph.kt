package com.gab.feature_playlists.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.gab.music_entities_module.PlaylistInfoModel
import com.google.gson.Gson

internal fun NavGraphBuilder.playlistNavGraph(
    allPlaylistsGridContent: @Composable () -> Unit,
    playlistScreenContent: @Composable (Long) -> Unit,
    playlistEditOrAddScreenContent: @Composable (PlaylistInfoModel, PlaylistChangesScreenMode) -> Unit,
) {
    navigation(
        startDestination = Screen.AllPlaylistsScreen.route,
        route = Screen.Playlist.route
    ) {
        composable(route = Screen.AllPlaylistsScreen.route) {
            allPlaylistsGridContent()
        }
        composable(
            route = Screen.PlaylistEditOrAddScreen.route,
            arguments = listOf(
                navArgument(Screen.KEY_PLAYLIST_EDIT_OR_ADD) {
                },
                navArgument(Screen.KEY_PLAYLIST_EDIT_OR_ADD_SCREEN_MODE) {}
            )
        ) {
            val playlistJson = it.arguments?.getString(Screen.KEY_PLAYLIST_EDIT_OR_ADD) ?: ""
            val playlist = PlaylistInfoModel.fromJson(playlistJson)
            val screenModeJson =
                it.arguments?.getString(Screen.KEY_PLAYLIST_EDIT_OR_ADD_SCREEN_MODE) ?: ""
            val screenMode = Gson().fromJson(screenModeJson, PlaylistChangesScreenMode::class.java)
            playlistEditOrAddScreenContent(playlist, screenMode)
        }
        composable(
            route = Screen.SinglePlaylistScreen.route,
            arguments = listOf(
                navArgument(Screen.KEY_PLAYLIST) {
                    type = NavType.LongType
                }
            )
        ) {
            val playlistId = it.arguments?.getLong(Screen.KEY_PLAYLIST) ?: 0L
            playlistScreenContent(playlistId)
        }
    }
}