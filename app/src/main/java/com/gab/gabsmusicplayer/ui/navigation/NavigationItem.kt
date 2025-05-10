package com.gab.gabsmusicplayer.ui.navigation

import com.gab.gabsmusicplayer.R

sealed class NavigationItem(
    val screen: Screen,
    val titleResId: Int
) {

    data object Playlists : NavigationItem(
        screen = Screen.AllPlaylistsScreen,
        titleResId = R.string.playlists_title
    )

    data object AllTracks: NavigationItem(
        screen = Screen.AllTracksScreen,
        titleResId = R.string.downloads_title
    )
}