package com.gab.feature_player.holder

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gab.feature_api_player.FeaturePlayerHolder
import com.gab.feature_player.ui.MiniPlayerImpl
import com.gab.feature_player.ui.MusicPlayerScreenImpl
import com.gab.feature_player.ui.PlayerViewModel
import javax.inject.Inject

public class FeaturePlayerHolderImpl @Inject constructor(
    private val viewModelFactory: ViewModelProvider.Factory
): FeaturePlayerHolder {
    @Composable
    override fun MiniPlayer(modifier: Modifier) {
        Base {
            MiniPlayerImpl(viewModel = it, modifier = modifier)
        }
    }

    @Composable
    override fun MusicPlayerScreen(modifier: Modifier) {
        Base {
            MusicPlayerScreenImpl(viewModel = it, modifier = modifier)
        }
    }

    @Composable
    private fun Base(content: @Composable (PlayerViewModel) -> Unit) {
        val viewModel = viewModel<PlayerViewModel>(factory = viewModelFactory)
        content(viewModel)
    }
}