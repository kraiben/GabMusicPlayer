package com.gab.gabsmusicplayer.ui.allTracksScreen

import com.gab.core_music_loading.models.TrackInfoModel


sealed class AllTracksScreenState {
    data object Initial : AllTracksScreenState()

    data object DataIsLoading : AllTracksScreenState()

    data class Tracks(
        val tracks: List<TrackInfoModel>,
    ) : AllTracksScreenState()
}