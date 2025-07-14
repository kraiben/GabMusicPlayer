package com.gab.gabsmusicplayer.ui.general

import androidx.compose.runtime.Stable
import com.gab.gabsmusicplayer.domain.models.TrackInfoModel

@Stable
sealed class CurrentTrackState {

    data object Initial : CurrentTrackState()

    data object NoCurrentTrack : CurrentTrackState()

    @Stable
    class Track(val track: TrackInfoModel, val isPaused: Boolean) : CurrentTrackState()

}