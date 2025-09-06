package com.gab.feature_playlists.ui.navigation

import androidx.navigation.NavHostController
import com.gab.music_entities_module.PlaylistInfoModel

internal object NavControllerExtras {

    internal fun NavHostController.navigateToSinglePlaylistScreen(playlistId: Long) {
        this.navigate(Screen.SinglePlaylistScreen.getRouteWithArgs(playlistId))
    }

    internal fun NavHostController.navigateToPlaylistEditScreen(
        playlist: PlaylistInfoModel
    ) {
        this.navigate(
            Screen.PlaylistEditOrAddScreen.getRouteWithArgs(
                playlist,
                PlaylistChangesScreenMode.EditMode
            )
        )
    }
    internal fun NavHostController.navigateToPlaylistCreatingScreen() {
        this.navigate(
            Screen.PlaylistEditOrAddScreen.getRouteWithArgs(
                PlaylistInfoModel.EMPTY,
                PlaylistChangesScreenMode.CreatingMode
            )
        )
    }

    internal fun NavHostController.returnFromPlaylistEditOrAddScreen() {
        if (this.currentDestination?.route == Screen.PlaylistEditOrAddScreen.route) {
            this.popBackStack()
        }
    }
    internal fun NavHostController.returnFromPlaylistEditOrAddScreenAfterRemovingPlaylist() {
        this.navigate(Screen.Playlist.route) {
            popUpTo(Screen.Playlist.route) {
                inclusive = true
                saveState = false
            }
        }
    }
}