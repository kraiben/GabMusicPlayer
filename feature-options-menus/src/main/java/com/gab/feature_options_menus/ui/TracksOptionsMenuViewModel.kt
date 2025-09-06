package com.gab.feature_options_menus.ui

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gab.model_media_usecases.usecases_api.SetNextTrackUseCase
import com.gab.model_module.usecases.AddToPlaylistUseCase
import com.gab.model_module.usecases.CreatePlaylistUseCase
import com.gab.model_module.usecases.GetPlaylistsUseCase
import com.gab.model_module.usecases.RemoveFromPlaylistUseCase
import com.gab.music_entities_module.PlaylistInfoModel
import com.gab.music_entities_module.TrackInfoModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

public class TracksOptionsMenuViewModel @Inject constructor(
    private val createPlaylistUseCase: CreatePlaylistUseCase,
    private val setNextTrackUseCase: SetNextTrackUseCase,
    private val addToPlaylistUseCase: AddToPlaylistUseCase,
    private val removeFromPlaylistUseCase: RemoveFromPlaylistUseCase,
    private val getPlaylistsUseCase: GetPlaylistsUseCase,
) : ViewModel() {

    private val playlists: StateFlow<List<PlaylistInfoModel>> = getPlaylistsUseCase()
    internal fun getPlaylists(): StateFlow<List<PlaylistInfoModel>> = playlists

    internal fun addToPlaylist(playlist: PlaylistInfoModel, track: TrackInfoModel) {
        viewModelScope.launch {
            addToPlaylistUseCase(playlist = playlist, track = track)
        }
    }

    internal fun removeFromPlaylist(playlist: PlaylistInfoModel, track: TrackInfoModel) {
        viewModelScope.launch {
            removeFromPlaylistUseCase(playlist = playlist, track = track)
        }
    }

    internal fun setNextTrack(track: TrackInfoModel) {
        viewModelScope.launch {
            setNextTrackUseCase(track)
        }
    }

    private val _createPlaylistResultFlow = MutableSharedFlow<Boolean>()
    internal val createPlaylistResultFlow = flow {
        _createPlaylistResultFlow.collect {
            emit(it)
        }
    }.shareIn(viewModelScope, SharingStarted.Lazily)

    internal fun createPlaylist(track: TrackInfoModel, title: String,) {
        viewModelScope.launch {
            _createPlaylistResultFlow.emit(
                createPlaylistUseCase(
                    tracks = listOf(track),
                    title = title,
                    coverUri = Uri.EMPTY,
                )
            )
        }
    }
}