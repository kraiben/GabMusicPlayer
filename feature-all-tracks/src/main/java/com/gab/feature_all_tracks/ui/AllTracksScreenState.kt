package com.gab.feature_all_tracks.ui

import com.gab.music_entities_module.TrackInfoModel

internal sealed class AllTracksScreenState {

    data object Initial: AllTracksScreenState()

    data object Loading: AllTracksScreenState()

    data class Tracks(val tracks: List<TrackInfoModel>): AllTracksScreenState()
}