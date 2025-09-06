package com.gab.feature_playlists.ui

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gab.feature_playlists.ui.components.PlaylistsScreenState
import com.gab.model_media_usecases.usecases_api.SetRandomTrackQueueUseCase
import com.gab.model_media_usecases.usecases_api.SetTrackQueueUseCase
import com.gab.model_module.usecases.ChangePlaylistUseCase
import com.gab.model_module.usecases.CreatePlaylistUseCase
import com.gab.model_module.usecases.GetPlaylistsUseCase
import com.gab.model_module.usecases.GetTracksWithoutDurationFilterUseCase
import com.gab.model_module.usecases.RemovePlaylistUseCase
import com.gab.model_module.usecases.SetPlaylistPictureUseCase
import com.gab.music_entities_module.PlaylistInfoModel
import com.gab.music_entities_module.TrackInfoModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

public class PlaylistsFeatureViewModel @Inject constructor(
    private val getPlaylistsUseCase: GetPlaylistsUseCase,
    private val createPlaylistUseCase: CreatePlaylistUseCase,
    private val removePlaylistUseCase: RemovePlaylistUseCase,
    private val changePlaylistsUseCase: ChangePlaylistUseCase,

    private val setTrackQueueUseCase: SetTrackQueueUseCase,
    private val setRandomTrackQueueUseCase: SetRandomTrackQueueUseCase,
    private val setPlaylistPictureUseCase: SetPlaylistPictureUseCase,
    private val getTracksWithoutDurationFilterUseCase: GetTracksWithoutDurationFilterUseCase,
) : ViewModel() {

    private val playlists = getPlaylistsUseCase().map { PlaylistsScreenState.Playlists(it) }
        .stateIn(viewModelScope, SharingStarted.Lazily, PlaylistsScreenState.Loading)
    internal fun getPlaylists(): StateFlow<PlaylistsScreenState> = playlists

    private val tracks = getTracksWithoutDurationFilterUseCase()

    internal fun getTracks(): StateFlow<List<TrackInfoModel>> = tracks

    private val _playlistCreationOrEditingResultFlow = MutableSharedFlow<Boolean>()
    internal val playlistCreationOrEditingResultFlow = flow {
        _playlistCreationOrEditingResultFlow.collect {
            emit(it)
        }
    }.shareIn(viewModelScope, SharingStarted.Lazily)


    internal fun createPlaylist(tracks: List<TrackInfoModel>, title: String, imageCover: Uri) {
        viewModelScope.launch {
            _playlistCreationOrEditingResultFlow.emit(
                createPlaylistUseCase(
                    tracks = tracks,
                    title = title,
                    coverUri = imageCover
                )
            )
        }
    }

    internal fun changePlaylist(
        playlistId: Long,
        tracks: List<TrackInfoModel>,
        title: String,
        coverUri: Uri,
    ) {
        viewModelScope.launch {
            _playlistCreationOrEditingResultFlow.emit(
                changePlaylistsUseCase(
                    playlistId = playlistId,
                    tracks = tracks,
                    title = title,
                    coverUri = coverUri
                )
            )
        }
    }
    internal fun removePlaylist(playlist: PlaylistInfoModel) {
        viewModelScope.launch {
            removePlaylistUseCase(playlist)
        }
    }
    internal fun setTrackQueue(tracks: List<TrackInfoModel>, startIndex: Int) {
        viewModelScope.launch {
            setTrackQueueUseCase(tracks = tracks, startIndex = startIndex)
        }
    }

    internal fun setRandomTracksQueue(tracks: List<TrackInfoModel>) {
        viewModelScope.launch {
            setRandomTrackQueueUseCase(tracks)
        }
    }
    internal fun setPlaylistPicture(playlist: PlaylistInfoModel, uri: Uri) {
        viewModelScope.launch {
            setPlaylistPictureUseCase(playlist, uri)
        }
    }
}