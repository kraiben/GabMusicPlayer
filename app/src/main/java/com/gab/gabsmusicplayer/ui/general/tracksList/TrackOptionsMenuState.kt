package com.gab.gabsmusicplayer.ui.general.tracksList

import com.gab.core_music_loading.models.PlaylistInfoModel
import com.gab.core_music_loading.models.TrackInfoModel

sealed class TrackOptionsMenuState(val isVisible: Boolean) {

    data object NotVisible : TrackOptionsMenuState(false)

    data class AllTracksScreenMenu(val track: TrackInfoModel) : TrackOptionsMenuState(true)

    data class PlaylistScreenMenu(val track: TrackInfoModel, val playlist: PlaylistInfoModel) : TrackOptionsMenuState(true)

    data class AddToPlaylistMenu(val track: TrackInfoModel) : TrackOptionsMenuState(true)

    data class AddToNewPlaylistMenu(val track: TrackInfoModel) : TrackOptionsMenuState(true)
}