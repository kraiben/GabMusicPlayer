package com.gab.feature_options_menus.holder

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gab.music_entities_module.PlaylistInfoModel
import com.gab.music_entities_module.TrackInfoModel

public interface TrackOptionsMenuFeatureHolder {

    @Composable
    public fun AllTracksScreenOptionsMenu(
        modifier: Modifier = Modifier,
        track: TrackInfoModel,
        onDismiss: () -> Unit,
    )

    @Composable
    public fun PlaylistScreenMenu(
        modifier: Modifier = Modifier,
        track: TrackInfoModel,
        playlist: PlaylistInfoModel,
        ondDismiss: () -> Unit,
    )

}