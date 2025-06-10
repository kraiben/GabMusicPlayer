package com.gab.gabsmusicplayer.ui.allTracksScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gab.gabsmusicplayer.domain.models.TrackInfoModel
import com.gab.gabsmusicplayer.ui.ViewModelFactory
import com.gab.gabsmusicplayer.ui.general.LoadingCircle
import com.gab.gabsmusicplayer.ui.general.tracksList.TrackListElement
import com.gab.gabsmusicplayer.ui.general.tracksList.TrackOptionsMenu
import com.gab.gabsmusicplayer.utils.GAB_CHECK
import kotlinx.coroutines.launch


@Composable
fun AllTracksScreen(
    viewModelFactory: ViewModelFactory,
    onTrackClickListener: (List<TrackInfoModel>, Int) -> Unit,
    onStartRandomButtonClickListener: (List<TrackInfoModel>) -> Unit,
    modifier: Modifier = Modifier,
    menuButtonClickListener: () -> Unit = {},
    addInOrderOption: (TrackInfoModel) -> Unit = {},
    snackbarHostState: SnackbarHostState,
) {
    val viewModel: AllTracksViewModel = viewModel(factory = viewModelFactory)
    GAB_CHECK("AllTracksScreen Recomposed")
    val scope = rememberCoroutineScope()
    val tracksState = viewModel.tracks.collectAsState(AllTracksScreenState.DataIsLoading)
    var currentTrack by remember { mutableStateOf<TrackInfoModel?>(null) }
    val isMenuOpened = remember { mutableStateOf(false) }
    when (val tracks = tracksState.value) {
        is AllTracksScreenState.Tracks -> {
            Box(
                modifier = modifier
            ) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .height(44.dp)
                                .clickable { onStartRandomButtonClickListener(tracks.tracks) }) {
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
                    items(items = tracks.tracks, key = { it.id }) {
                        TrackListElement(
                            it,
                            Modifier
                                .combinedClickable(
                                    onClick = {
                                        onTrackClickListener(
                                            tracks.tracks, tracks.tracks.indexOf(
                                                it
                                            )
                                        )
                                    },
                                    onLongClick = {
                                        scope.launch { snackbarHostState.showSnackbar("Добавлено в очередь") }
                                        addInOrderOption(it)
                                    }
                                )
                                .fillMaxWidth()
                                .height(60.dp),
                            menuButtonClickListener = {
                                currentTrack = it
                                isMenuOpened.value = true
                            }
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }

                if (
                    isMenuOpened.value
                ) {
                    currentTrack?.let { ct ->
                        GAB_CHECK(ct.title)
                        TrackOptionsMenu(
                            addInQueryOption = { addInOrderOption(ct) },
                            onDismiss = {
                                currentTrack = null
                                isMenuOpened.value = false
                            }
                        )
                    }

                }
            }
        }

        else -> LoadingCircle()
    }
}