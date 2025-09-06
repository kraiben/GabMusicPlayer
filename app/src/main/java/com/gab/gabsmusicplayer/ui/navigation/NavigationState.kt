package com.gab.gabsmusicplayer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gab.music_entities_module.PlaylistInfoModel

class NavigationState(
    val navHostController: NavHostController,
) {
    fun navigateTo(route: String) {
        navHostController.navigate(route) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }

    fun navigateToPlaylist(playlistId: Long) {
        navHostController.navigate(Screen.SinglePlaylistScreen.getRouteWithArgs(playlistId))
    }

    fun navigateToPlaylistEditScreen(
        playlist: PlaylistInfoModel,
//        screenMode: PlaylistChangesScreenMode,

        ) {
//        navHostController.navigate(
//            Screen.PlaylistEditOrAddScreen.getRouteWithArgs(
//                playlist,
//                screenMode
//            )
//        )
    }

    fun returnFromPlaylistEditOrAddScreen() {
        if (navHostController.currentDestination?.route == Screen.PlaylistEditOrAddScreen.route) {
            navHostController.popBackStack()
        }
    }
    fun returnFromPlaylistEditOrAddScreenAfterRemovingPlaylist() {
        navHostController.navigate(Screen.Playlist.route) {
            popUpTo(Screen.Playlist.route) {
                inclusive = true
                saveState = false
            }
        }
    }
}

@Composable
fun rememberNavigationState(
    navHostController: NavHostController = rememberNavController(),
): NavigationState {
    return remember {
        NavigationState(navHostController)
    }
}

