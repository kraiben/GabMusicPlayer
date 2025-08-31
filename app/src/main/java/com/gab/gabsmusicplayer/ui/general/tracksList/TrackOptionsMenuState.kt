package com.gab.gabsmusicplayer.ui.general.tracksList

import com.gab.music_entities_module.TrackInfoModel
import com.gab.music_entities_module.PlaylistInfoModel

sealed class TrackOptionsMenuState(val isVisible: Boolean) {

    data object NotVisible : TrackOptionsMenuState(false)

    data class AllTracksScreenMenu(val track: TrackInfoModel) : TrackOptionsMenuState(true)

    data class PlaylistScreenMenu(val track: TrackInfoModel, val playlist: PlaylistInfoModel) : TrackOptionsMenuState(true)

    data class AddToPlaylistMenu(val track: TrackInfoModel) : TrackOptionsMenuState(true)

    data class AddToNewPlaylistMenu(val track: TrackInfoModel) : TrackOptionsMenuState(true)
}