package com.gab.feature_api_player

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

public interface FeaturePlayerHolder {

    @Composable
    public fun MiniPlayer(modifier: Modifier = Modifier)
    @Composable
    public fun MusicPlayerScreen(modifier: Modifier = Modifier)
}