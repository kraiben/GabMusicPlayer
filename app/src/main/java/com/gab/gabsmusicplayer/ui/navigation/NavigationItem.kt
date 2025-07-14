package com.gab.gabsmusicplayer.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.PlaylistPlay
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.gab.gabsmusicplayer.R

sealed class NavigationItem(
    val screen: Screen,
    val titleResId: Int,
    val icon: ImageVector,
) {

    data object AllPlaylists : NavigationItem(
        screen = Screen.AllPlaylistsScreen,
        titleResId = R.string.playlists_title,
        icon = Icons.AutoMirrored.Filled.PlaylistPlay
    )

    data object AllTracks : NavigationItem(
        screen = Screen.AllTracksScreen,
        titleResId = R.string.downloads_title,
        icon = Icons.Default.Home
    )

}