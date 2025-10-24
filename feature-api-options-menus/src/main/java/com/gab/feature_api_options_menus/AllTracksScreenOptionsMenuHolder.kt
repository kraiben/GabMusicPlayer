package com.gab.feature_api_options_menus

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gab.music_entities_module.TrackInfoModel

public interface AllTracksScreenOptionsMenuHolder {

    @Composable
    public fun AllTracksScreenOptionsMenu(
        track: TrackInfoModel,
        modifier: Modifier = Modifier,
        onDismiss: () -> Unit,
    )

}