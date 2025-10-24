package com.gab.feature_api_options_menus

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gab.music_entities_module.PlaylistInfoModel
import com.gab.music_entities_module.TrackInfoModel

public interface PlaylistScreenMenuHolder {
    @Composable
    public fun PlaylistScreenMenu(
        playlist: PlaylistInfoModel,
        track: TrackInfoModel,
        modifier: Modifier = Modifier,
        onDismiss: () -> Unit
    )
}