package com.gab.gabsmusicplayer.ui.playlistScreens

import com.gab.gabsmusicplayer.domain.models.PlaylistInfoModel

sealed class PlaylistScreenState {
    data object Initial: PlaylistScreenState()

    data object Loading: PlaylistScreenState()

    data class Playlists(val playlists: List<PlaylistInfoModel>): PlaylistScreenState()
}