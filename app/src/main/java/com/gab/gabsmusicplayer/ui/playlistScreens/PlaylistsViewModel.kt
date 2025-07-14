package com.gab.gabsmusicplayer.ui.playlistScreens

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gab.gabsmusicplayer.domain.models.PlaylistInfoModel
import com.gab.gabsmusicplayer.domain.models.TrackInfoModel
import com.gab.gabsmusicplayer.domain.usecases.AddToPlaylistUseCase
import com.gab.gabsmusicplayer.domain.usecases.ChangePlaylistUseCase
import com.gab.gabsmusicplayer.domain.usecases.CheckIfPlaylistUniqueForCreationUseCase
import com.gab.gabsmusicplayer.domain.usecases.CheckIfPlaylistUniqueForEditCase
import com.gab.gabsmusicplayer.domain.usecases.CreatePlaylistUseCase
import com.gab.gabsmusicplayer.domain.usecases.GetPlaylistsUseCase
import com.gab.gabsmusicplayer.domain.usecases.GetTracksUseCaseWithDurationFilter
import com.gab.gabsmusicplayer.domain.usecases.GetTracksUseCaseWithoutDurationFilter
import com.gab.gabsmusicplayer.domain.usecases.RemoveFromPlaylistUseCase
import com.gab.gabsmusicplayer.domain.usecases.RemovePlaylistUseCase
import com.gab.gabsmusicplayer.domain.usecases.SetPlaylistPictureUseCase
import com.gab.gabsmusicplayer.ui.allTracksScreen.AllTracksScreenState
import com.gab.gabsmusicplayer.utils.GAB_CHECK
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlaylistsViewModel @Inject constructor(
    private val addToPlaylistUseCase: AddToPlaylistUseCase,
    private val changePlaylistUseCase: ChangePlaylistUseCase,
    private val createPlaylistUseCase: CreatePlaylistUseCase,
    private val getPlaylistsUseCase: GetPlaylistsUseCase,
    private val removeFromPlaylistUseCase: RemoveFromPlaylistUseCase,
    private val removePlaylistUseCase: RemovePlaylistUseCase,
    private val setPlaylistPictureUseCase: SetPlaylistPictureUseCase,
    private val getTracksUseCaseWithDurationFilter: GetTracksUseCaseWithDurationFilter,
    private val getTracksUseCaseWithoutDurationFilter: GetTracksUseCaseWithoutDurationFilter,
    private val checkIfPlaylistUniqueForCreationUseCase: CheckIfPlaylistUniqueForCreationUseCase,
    private val checkIfPlaylistUniqueForEditUseCase: CheckIfPlaylistUniqueForEditCase,
) : ViewModel() {

    init {
        GAB_CHECK("PlaylistsViewModel initialized")
    }

    val tracks = getTracksUseCaseWithDurationFilter().map {
        AllTracksScreenState.Tracks(it) as AllTracksScreenState
    }
        .onStart { emit(AllTracksScreenState.DataIsLoading) }

    val tracksWithoutDurationFilter = getTracksUseCaseWithoutDurationFilter()

    val playlists = getPlaylistsUseCase().map {
        PlaylistScreenState.Playlists(it) as PlaylistScreenState
    }
        .onStart { emit(PlaylistScreenState.Loading) }

    fun addToPlaylist(playlist: PlaylistInfoModel, trackInfoModel: TrackInfoModel) {
        viewModelScope.launch {
            addToPlaylistUseCase(playlist, trackInfoModel)
        }
    }

    private val _playlistCreationOrEditingResultFlow = MutableSharedFlow<Boolean>()
    val playlistCreationOrEditingResultFlow = flow {
        _playlistCreationOrEditingResultFlow.collect{emit(it)}
    }
    fun changePlaylist(
        playlistId: Long,
        tracks: List<TrackInfoModel>,
        title: String,
        coverUri: Uri,) {
        viewModelScope.launch {
            _playlistCreationOrEditingResultFlow.emit(changePlaylistUseCase(playlistId, tracks, title, coverUri))

        }
    }
    fun createPlaylist(tracks: List<TrackInfoModel>, title: String, coverUri: Uri) {
        viewModelScope.launch {
            _playlistCreationOrEditingResultFlow.emit(createPlaylistUseCase( tracks = tracks, title = title, coverUri = coverUri))
        }
    }

    fun removeFromPlaylist(playlist: PlaylistInfoModel, trackInfoModel: TrackInfoModel) {
        viewModelScope.launch {
            removeFromPlaylistUseCase(playlist, trackInfoModel)
        }
    }

    fun removePlaylist(playlist: PlaylistInfoModel) {
        viewModelScope.launch {
            removePlaylistUseCase(playlist)
        }
    }


    override fun onCleared() {
        GAB_CHECK("PlaylistsViewModel cleared")
        super.onCleared()
    }

    fun setPlaylistPicture(uri: Uri, playlist: PlaylistInfoModel) {
        viewModelScope.launch { setPlaylistPictureUseCase(playlist = playlist, uri = uri) }
    }
}