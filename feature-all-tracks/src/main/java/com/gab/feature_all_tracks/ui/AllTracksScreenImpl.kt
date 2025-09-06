package com.gab.feature_all_tracks.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gab.feature_all_tracks.GAB_CHECK
import com.gab.music_entities_module.TrackInfoModel

@Composable
internal fun AllTracksScreenImpl(
    modifier: Modifier = Modifier,
    menuButtonClickListener: () -> Unit = {},
    trackOptionsMenuContent: @Composable ((track: TrackInfoModel, onDismiss: () -> Unit) -> Unit),
    trackAddedToQueryMessage: (String) -> Unit,
    viewModelFactory: ViewModelProvider.Factory,
) {
    val viewModel = viewModel<AllTracksViewModel>(factory = viewModelFactory)
    val screenState = viewModel.getTracks().collectAsState(AllTracksScreenState.Initial)
    when (val tracksState = screenState.value) {
        is AllTracksScreenState.Tracks -> {
            val trackListElementColors = ListItemDefaults.colors(
                containerColor = Color.Transparent
            )
            val trackListElementModifier = remember {
                Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            }
            var sortState by remember { mutableStateOf<SortParameter?>(null) }
            val sortedTracks = remember(sortState, tracksState.tracks) {
                when (sortState) {
                    SortParameter.Date -> tracksState.tracks.sortedByDescending{it.dateAdded}
                    SortParameter.Title -> tracksState.tracks.sortedBy { it.title }
                    null -> tracksState.tracks.sortedBy { it.id }
                }
            }

            var currentOpenedMenuTrack by remember { mutableStateOf<TrackInfoModel?>(null) }
            val openTrackOptionsMenu =
                remember { { t: TrackInfoModel -> currentOpenedMenuTrack = t } }
            val setTracksQueue = remember() {
                { index: Int, tracks: List<TrackInfoModel> ->
                    viewModel.setTrackQueue(
                        tracks,
                        index
                    )
                }
            }
            val setNextTrack = remember { { t: TrackInfoModel -> viewModel.setNextTrack(t) } }
            Box() {
                LazyColumn(
                    modifier = modifier.background(color = MaterialTheme.colorScheme.background)
                ) {
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .height(56.dp)
                                .clickable { viewModel.setRandomTracksQueue(tracksState.tracks) }) {
                            Icon(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .aspectRatio(1f)
                                    .clickable {
                                        menuButtonClickListener()
                                    },
                                contentDescription = null,
                                imageVector = Icons.Default.Menu
                            )
                            Icon(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .aspectRatio(1f),
                                imageVector = Icons.Filled.PlayCircleFilled,
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Играть в случайном порядке",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.W300,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1f)
                            )
                        }
                    }
                    item {
                        SortChipsRow(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = remember {
                                { sp: SortParameter ->
                                    sortState = if (sortState == sp) null else sp
                                }
                            },
                            selectedSortParameter = sortState
                        )
                    }
                    itemsIndexed(
                        items = sortedTracks,
                        key = { index: Int, t: TrackInfoModel -> t.id },
                    ) { index: Int, t: TrackInfoModel ->
                        TrackListElement(
                            track = t,
                            modifier = trackListElementModifier
                                .combinedClickable(
                                    onClick = {
                                        setTracksQueue(index, sortedTracks)
                                    },
                                    onLongClick = {
                                        trackAddedToQueryMessage("Добавлено в очередь")
                                        setNextTrack(t)
                                    }
                                ),
                            menuButtonClickListener = openTrackOptionsMenu,
                            colors = trackListElementColors
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
                currentOpenedMenuTrack?.let {
                    trackOptionsMenuContent(it) {
                        GAB_CHECK("onDismiss options menu")
                        currentOpenedMenuTrack = null
                    }
                }
            }
        }
        else -> {
            LoadingCircle()
        }
    }
}

