package com.gab.feature_api_all_tracks

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder

public interface AllTTracksFeatureHolder {

    public fun NavGraphBuilder.allTracksScreen(
        modifier: Modifier = Modifier.Companion,
        onMenuButtonClick: () -> Unit,
        trackAddedToQueryMessage: (String) -> Unit
    )

    public fun getAllTracksScreenRoute(): String
}