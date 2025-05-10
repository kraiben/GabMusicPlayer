package com.gab.gabsmusicplayer.ui.allTracksScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gab.gabsmusicplayer.domain.models.TrackInfoModel
import com.gab.gabsmusicplayer.ui.ViewModelFactory
import com.gab.gabsmusicplayer.ui.general.LoadingCircle
import com.gab.gabsmusicplayer.ui.general.tracksList.TrackListElement
import com.gab.gabsmusicplayer.utils.GAB_CHECK


@Composable
fun AllTracksScreen(
    viewModelFactory: ViewModelFactory,
    onTrackClickListener: (List<TrackInfoModel>, Int) -> Unit
) {
    val viewModel: AllTracksViewModel = viewModel(factory = viewModelFactory)

    val tracksState = viewModel.tracks.collectAsState(AllTracksScreenState.DataIsLoading)

    when (val tracks = tracksState.value) {
        is AllTracksScreenState.Tracks -> {
            GAB_CHECK(tracks.tracks.size)
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(items = tracks.tracks, key = { it.id }) {
                    TrackListElement(it, Modifier.clickable {
                        onTrackClickListener( tracks.tracks, tracks.tracks.indexOf(
                            it
                        ) )
                    })
                    HorizontalDivider(
                        thickness = 0.5.dp,
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                }
            }
        }
        else -> LoadingCircle()
    }

}