package com.gab.gabsmusicplayer.ui.allTracksScreen

import com.gab.gabsmusicplayer.domain.models.TrackInfoModel

sealed class AllTracksScreenState {
    data object Initial : AllTracksScreenState()

    data object DataIsLoading : AllTracksScreenState()

    data class Tracks(
        val tracks: List<TrackInfoModel>,
    ) : AllTracksScreenState()
}