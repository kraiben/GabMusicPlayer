package com.gab.gabsmusicplayer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gab.gabsmusicplayer.domain.models.PlaylistInfoModel
import com.gab.gabsmusicplayer.ui.playlistScreens.PlaylistChangesScreenMode

@Composable
fun MusicNavGraph(
    navHostController: NavHostController,
    allTracksScreenContent: @Composable () -> Unit,
    allPlaylistsGridContent: @Composable () -> Unit,
    playlistScreenContent: @Composable (Long) -> Unit,
    playlistEditOrAddScreenContent: @Composable (PlaylistInfoModel, PlaylistChangesScreenMode) -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Playlist.route
//        startDestination = Screen.AllTracksScreen.route
    ) {
        composable(
            route = Screen.AllTracksScreen.route
        ) {
            allTracksScreenContent()
        }
        playlistNavGraph(
            allPlaylistsGridContent = allPlaylistsGridContent,
            playlistScreenContent = playlistScreenContent,
            playlistEditOrAddScreenContent = playlistEditOrAddScreenContent
        )
    }
}