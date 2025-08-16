package com.gab.gabsmusicplayer.ui.allTracksScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gab.gabsmusicplayer.ui.general.LoadingCircle
import com.gab.gabsmusicplayer.ui.general.tracksList.TrackListElement
import com.gab.gabsmusicplayer.utils.GAB_CHECK
import com.gab.core_music_loading.models.TrackInfoModel
import kotlinx.coroutines.launch


@Composable
fun AllTracksScreen(
    onTrackClickListener: (List<TrackInfoModel>, Int) -> Unit,
    onStartRandomButtonClickListener: (List<TrackInfoModel>) -> Unit,
    padding: PaddingValues = PaddingValues(0.dp),
    menuButtonClickListener: () -> Unit = {},
    trackOptionsMenuClickListener: (TrackInfoModel) -> Unit,
    addInOrderOption: (TrackInfoModel) -> Unit = {},
    snackbarHostState: SnackbarHostState,
    tracksState: AllTracksScreenState,
) {
    GAB_CHECK("AllTracksScreen Recomposed")
    val scope = rememberCoroutineScope()

    val snackbarMessage = remember { { scope.launch { snackbarHostState.showSnackbar("Добавлено в очередь") } } }
    when (tracksState) {

        is AllTracksScreenState.Tracks -> {
            val trackListElementModifier = remember {
                Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            }
            val trackListElementColors = ListItemDefaults.colors(
                    containerColor = Color.Transparent
                )

            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues = padding)
                    .fillMaxSize()
            ) {
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .height(56.dp)
                            .clickable { onStartRandomButtonClickListener(tracksState.tracks) }) {
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

                itemsIndexed(
                    items = tracksState.tracks,
                    key = { index: Int, t: TrackInfoModel -> t.id },
                ) { index: Int, t: TrackInfoModel  ->
                    TrackListElement(
                        track = t,
                        modifier = trackListElementModifier
                            .combinedClickable(
                                onClick = {
                                    onTrackClickListener(
                                        tracksState.tracks, index
                                    )
                                },
                                onLongClick = {
                                    snackbarMessage()
                                    addInOrderOption(t)
                                }
                            ),
                        menuButtonClickListener = { trackOptionsMenuClickListener(t) },
                        colors = trackListElementColors
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }

        }

        else -> LoadingCircle()
    }
}