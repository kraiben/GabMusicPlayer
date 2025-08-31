package com.gab.feature_playlists.ui

import com.gab.music_entities_module.PlaylistInfoModel

sealed class PlaylistScreenState {
    data object Initial: PlaylistScreenState()

    data object Loading: PlaylistScreenState()

    data class Playlists(val playlists: List<PlaylistInfoModel>): PlaylistScreenState()
}