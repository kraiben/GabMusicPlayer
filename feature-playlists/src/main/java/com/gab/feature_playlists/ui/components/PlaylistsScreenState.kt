package com.gab.feature_playlists.ui.components

import com.gab.music_entities_module.PlaylistInfoModel

internal sealed class PlaylistsScreenState {
    data object Initial: PlaylistsScreenState()

    data object Loading: PlaylistsScreenState()

    data class Playlists(val playlists: List<PlaylistInfoModel>): PlaylistsScreenState()
}