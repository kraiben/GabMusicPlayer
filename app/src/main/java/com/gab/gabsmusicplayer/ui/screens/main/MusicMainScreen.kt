package com.gab.gabsmusicplayer.ui.screens.main

import android.Manifest
import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.util.UnstableApi
import com.gab.gabsmusicplayer.ui.ViewModelFactory
import com.gab.gabsmusicplayer.ui.allTracksScreen.AllTracksScreen
import com.gab.gabsmusicplayer.ui.musicPlayerScreen.MusicPlayerScreen
import com.gab.gabsmusicplayer.ui.navigation.MusicNavGraph
import com.gab.gabsmusicplayer.ui.navigation.rememberNavigationState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MusicMainScreen(viewModelFactory: ViewModelFactory) {
    val navigationState = rememberNavigationState()
    val context = LocalContext.current
    val readExtStoragePermissionState = rememberPermissionState(
        permission =
        if (Build.VERSION.SDK_INT >= 33) Manifest.permission.READ_MEDIA_AUDIO
        else Manifest.permission.READ_EXTERNAL_STORAGE
    )
    val viewModel: MainViewModel = viewModel(factory = viewModelFactory)
    GetPermission(readExtStoragePermissionState)
    if (readExtStoragePermissionState.status.isGranted) {
        MusicNavGraph(
            navHostController = navigationState.navHostController,

            audioPlayerScreenContent = {
                if (viewModel.currentMediaItem != null) {
                    MusicPlayerScreen(
                        isTrackPlaying = viewModel.isTrackPlaying,
                        currentPosition = viewModel.currentPosition,
                        title = viewModel.title,
                        artist = viewModel.artist,
                        duration = viewModel.duration,
                        imageUri = viewModel.imageUri,
                        isShuffled = viewModel.isShuffled,
                        isOneRepeating = viewModel.isRepeatingOne,
                        onNextButtonClick = { viewModel.nextTrack() },
                        onPreviousButtonClick = { viewModel.previousTrack() },
                        onPlayPauseButtonClick = { viewModel.playPauseChange() },
                        onSliderChange = { viewModel.onSliderChange(it) },
                        onShuffleButtonClick = { viewModel.shuffleStateChange() },
                        onRepeatModeButtonClick = { viewModel.isRepeatingOneStateChange() }
                    )
                }

            },

            allTracksScreenContent = {
                AllTracksScreen(
                    viewModelFactory,
                    onTrackClickListener = { p1, p2 ->
                        viewModel.selectTrack(
                            tracks = p1,
                            startIndex = p2,
                            context = context,
                            navigationState = navigationState
                        )
                    }
                )
            }
        )

    } else {
        Text(
            text = "Request Perms",
            fontSize = 40.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = { readExtStoragePermissionState.launchPermissionRequest() })
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GetPermission(permission: PermissionState) {

    LaunchedEffect(permission) {
        when (permission.status) {
            is PermissionStatus.Denied -> {
                permission.launchPermissionRequest()
            }

            PermissionStatus.Granted -> {}
        }
    }
}