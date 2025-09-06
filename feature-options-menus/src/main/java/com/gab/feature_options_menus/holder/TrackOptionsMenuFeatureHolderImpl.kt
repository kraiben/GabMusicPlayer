package com.gab.feature_options_menus.holder

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.gab.feature_options_menus.ui.AllTracksScreenOptionsMenuImpl
import com.gab.feature_options_menus.ui.PlaylistScreenMenuImpl
import com.gab.music_entities_module.PlaylistInfoModel
import com.gab.music_entities_module.TrackInfoModel
import javax.inject.Inject

public class TrackOptionsMenuFeatureHolderImpl @Inject constructor(
    private val viewModelFactory: ViewModelProvider.Factory
): TrackOptionsMenuFeatureHolder {
    @Composable
    override fun AllTracksScreenOptionsMenu(
        modifier: Modifier,
        track: TrackInfoModel,
        onDismiss: () -> Unit,
    ) {
        AllTracksScreenOptionsMenuImpl(
            modifier = modifier,
            track = track,
            onDismiss = onDismiss,
            viewModelFactory = viewModelFactory,
        )
    }

    @Composable
    override fun PlaylistScreenMenu(
        modifier: Modifier,
        track: TrackInfoModel,
        playlist: PlaylistInfoModel,
        ondDismiss: () -> Unit,
    ) {
        PlaylistScreenMenuImpl(
            modifier = modifier,
            track = track,
            onDismiss = ondDismiss,
            viewModelFactory = viewModelFactory,
            playlist = playlist
        )
    }
}