package com.gab.feature_playlists.ui

import android.Manifest
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Reply
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.outlined.AddBox
import androidx.compose.material.icons.outlined.IndeterminateCheckBox
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gab.feature_playlists.ui.components.SortChip
import com.gab.feature_playlists.ui.components.SortOder
import com.gab.feature_playlists.ui.components.SortOrderState
import com.gab.feature_playlists.ui.components.SortParameter
import com.gab.feature_playlists.ui.navigation.PlaylistChangesScreenMode
import com.gab.music_entities_module.PlaylistInfoModel
import com.gab.music_entities_module.TrackInfoModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState


@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
internal fun PlaylistEditOrAddScreen(
    modifier: Modifier = Modifier,
    viewModelFactory: ViewModelProvider.Factory,
    playlist: PlaylistInfoModel,
    screenMode: PlaylistChangesScreenMode,
    pictureSelectingErrorMsg: (String) -> Unit,
    onReturn: () -> Unit,
    onReturnAfterRemoving: () -> Unit,
) {

    val viewModel = viewModel<PlaylistsFeatureViewModel>(factory = viewModelFactory)
    val allTracks by viewModel.getTracks().collectAsState()

    var title by remember { mutableStateOf(playlist.title) }
    var coverUri by remember { mutableStateOf(playlist.coverUri) }
    val fallbackCover by remember { mutableIntStateOf(playlist.fallbackCover) }
    var coverImageKey by remember { mutableLongStateOf(System.currentTimeMillis()) }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            coverUri = uri
            coverImageKey = System.currentTimeMillis()
        } ?: pictureSelectingErrorMsg(
            "Ошибка выбора картинки"
        )

    }
    val permissionState =
        rememberPermissionState(if (Build.VERSION.SDK_INT <= 33) Manifest.permission.READ_EXTERNAL_STORAGE else Manifest.permission.READ_MEDIA_IMAGES) {
            imagePickerLauncher.launch(
                "image/*"
            )
        }
    val acceptedTracks = remember {
        mutableStateListOf<TrackInfoModel>().apply { addAll(playlist.tracks) }
    }
    val unacceptedTracks = remember {
        mutableStateListOf<TrackInfoModel>().apply {
            addAll(allTracks.filter { it !in acceptedTracks })
        }
    }
    var sortOrderState by remember {
        mutableStateOf(
            SortOrderState(
                SortOder.Asc,
                SortParameter.Date
            )
        )
    }

    var isAlertVisible by remember { mutableStateOf(false) }
    LaunchedEffect(sortOrderState) {
        when (sortOrderState) {
            SortOrderState(SortOder.Asc, SortParameter.Date) -> {
                acceptedTracks.sortBy { it.dateAdded }
                unacceptedTracks.sortBy { it.dateAdded }
            }

            SortOrderState(SortOder.Desc, SortParameter.Date) -> {
                acceptedTracks.sortByDescending { it.dateAdded }
                unacceptedTracks.sortByDescending { it.dateAdded }
            }

            SortOrderState(SortOder.Asc, SortParameter.Title) -> {
                acceptedTracks.sortBy { it.title }
                unacceptedTracks.sortBy { it.title }
            }

            SortOrderState(SortOder.Desc, SortParameter.Title) -> {
                acceptedTracks.sortByDescending { it.title }
                unacceptedTracks.sortByDescending { it.title }
            }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.playlistCreationOrEditingResultFlow.collect {
            if (it) {
                onReturn()
            }
        }
    }
    var areUnacceptedGoingFirst by remember { mutableStateOf(true) }
    val trackElementModifier = Modifier.height(50.dp).fillMaxWidth()

    val onAcceptedTracksOptionClick = remember { {t: TrackInfoModel ->
        acceptedTracks.remove(t)
        unacceptedTracks.add(t)
    } }
    val onUnAcceptedTracksOptionClick = remember { {t: TrackInfoModel ->
        unacceptedTracks.remove(t)
        acceptedTracks.add(t)
    } }
    Column(modifier = modifier) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.5f)
                    ) {
                        AsyncImage(
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth(0.66f)
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(12)), contentDescription = null,
                            model = (ImageRequest.Builder(LocalContext.current)
                                .data(coverUri)
                                .error(fallbackCover)
                                .memoryCacheKey("${coverUri.path}_${fallbackCover}-${coverImageKey}")
                                .build())
                        )
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(70.dp)
                                    .clickable {
                                        if (screenMode == PlaylistChangesScreenMode.EditMode) {
                                            isAlertVisible = true
                                        } else {
                                            onReturn()
                                        }
                                    },
                                contentDescription = null,
                                tint = when (screenMode) {
                                    PlaylistChangesScreenMode.CreatingMode -> MaterialTheme.colorScheme.primary
                                    PlaylistChangesScreenMode.EditMode -> Color.Red
                                },
                                imageVector = when (screenMode) {
                                    PlaylistChangesScreenMode.CreatingMode -> Icons.AutoMirrored.Filled.Reply
                                    PlaylistChangesScreenMode.EditMode -> Icons.Filled.DeleteForever
                                }
                            )
                            Icon(
                                modifier = Modifier
                                    .size(70.dp)
                                    .clickable {
                                        if (permissionState.status.isGranted) {
                                            imagePickerLauncher.launch("image/*")
                                        } else {
                                            permissionState.launchPermissionRequest()
                                        }
                                    },
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                imageVector = Icons.Default.Image
                            )
                        }
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        SortChip(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .fillMaxWidth(0.5f)
                                .wrapContentHeight(),
                            sortParameter = SortParameter.Title,
                            sortOrderState = sortOrderState,
                            onClick = {
                                sortOrderState =
                                    if (sortOrderState.parameter == SortParameter.Title) {
                                        sortOrderState.getInvertedOrder()
                                    } else {
                                        SortOrderState(SortOder.Asc, SortParameter.Title)
                                    }
                            })

                        SortChip(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .fillMaxWidth(1f)
                                .wrapContentHeight(),
                            sortParameter = SortParameter.Date,
                            sortOrderState = sortOrderState,
                            onClick = {
                                sortOrderState =
                                    if (sortOrderState.parameter == SortParameter.Date) {
                                        sortOrderState.getInvertedOrder()
                                    } else {
                                        SortOrderState(SortOder.Asc, SortParameter.Date)
                                    }
                            })
                    }
                    InputChip(
                        modifier = Modifier
                            .wrapContentHeight()
                            .padding(horizontal = 4.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(40),
                        selected = true,
                        onClick = { areUnacceptedGoingFirst = !areUnacceptedGoingFirst },
                        label = { Text(if (areUnacceptedGoingFirst) "Сперва недобавленные" else "Сперва добавленные") },
                        trailingIcon = {
                            Icon(
                                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                imageVector = if (!areUnacceptedGoingFirst) Icons.Outlined.IndeterminateCheckBox else Icons.Outlined.AddBox,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                        },
                        colors = InputChipDefaults.inputChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                    TextField(
                        value = title,
                        onValueChange = { title = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(
                                text = "Название плейлиста",
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    )
                }
            }
            if (areUnacceptedGoingFirst) {
                items(items = unacceptedTracks, key = { "${it.id} unaccepted" }) { track ->
                    PlaylistCreationTrackElement(
                        track = track,
                        modifier = trackElementModifier,
                        isAccepted = false
                    ) {
                        onUnAcceptedTracksOptionClick(track)
                    }
                }
                items(items = acceptedTracks, key = { "${it.id} accepted" }) { track ->
                    PlaylistCreationTrackElement(
                        track = track,
                        modifier = trackElementModifier,
                        isAccepted = true
                    ) {
                        onAcceptedTracksOptionClick(track)
                    }
                }
            } else {
                items(items = acceptedTracks, key = { "${it.id} accepted" }) { track ->
                    PlaylistCreationTrackElement(
                        track = track,
                        modifier = trackElementModifier,
                        isAccepted = true
                    ) {
                        onAcceptedTracksOptionClick(track)
                    }
                }
                items(items = unacceptedTracks, key = { "${it.id} unaccepted" }) { track ->
                    PlaylistCreationTrackElement(
                        track = track,
                        modifier = trackElementModifier,
                        isAccepted = false
                    ) {
                        onUnAcceptedTracksOptionClick(track)
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            Button(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .height(36.dp)
                    .fillMaxWidth(0.5f),
                onClick = onReturn,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "Отмена",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    textAlign = TextAlign.Center
                )
            }
            Button(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .height(36.dp)
                    .fillMaxWidth(1f),
                onClick = {
                    when (screenMode) {
                        PlaylistChangesScreenMode.EditMode -> {
                            viewModel.changePlaylist(
                                playlist.id,
                                acceptedTracks.toList(),
                                title,
                                coverUri
                            )
                        }

                        PlaylistChangesScreenMode.CreatingMode -> {
                            viewModel.createPlaylist(acceptedTracks.toList(), title, coverUri)
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "Сохранить",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
    AnimatedVisibility(isAlertVisible) {
        BasicAlertDialog(onDismissRequest = { isAlertVisible = false }) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(0.25f)
                    .fillMaxWidth(0.80f)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(16)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "Подтвердите удаление плейлиста ${playlist.title}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f)
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .fillMaxHeight()
                            .clickable { isAlertVisible = false }, text = "Отмена"
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .fillMaxHeight()
                            .clickable {
                                viewModel.removePlaylist(playlist)
                                isAlertVisible = false
                                onReturnAfterRemoving()
                            }, text = "Удалить"
                    )
                }
            }
        }
    }
}


@Composable
private fun PlaylistCreationTrackElement(
    track: TrackInfoModel,
    modifier: Modifier = Modifier,
    isAccepted: Boolean,
    acceptionChangingButtonClickListener: () -> Unit,
) {
    ListItem(
        colors = ListItemDefaults.colors(
            containerColor = Color.Transparent
        ),
        modifier = modifier,
        leadingContent = {
            AsyncImage(
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f), contentDescription = null,
                model = (ImageRequest.Builder(LocalContext.current)
                    .data(if (track.albumArtUri == Uri.EMPTY) track.albumArtUriIsNullPatchId else track.albumArtUri)
                    .memoryCacheKey(track.id.toString())
                    .build())
            )
        },

        headlineContent = {
            Text(
                color = MaterialTheme.colorScheme.onBackground,
                text = track.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleSmall
            )
        },
        supportingContent = {
            Text(
                color = MaterialTheme.colorScheme.onBackground,
                text = track.artist,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall
            )
        },
        trailingContent = {
            Icon(
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = null,
                imageVector = if (isAccepted) Icons.Outlined.IndeterminateCheckBox else Icons.Outlined.AddBox,
                modifier = Modifier
                    .clickable {
                        acceptionChangingButtonClickListener()
                    }
                    .padding(vertical = 8.dp)
                    .fillMaxHeight()
            )
        }

    )
}
