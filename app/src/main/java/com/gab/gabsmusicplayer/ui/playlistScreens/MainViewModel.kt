package com.gab.gabsmusicplayer.ui.playlistScreens

import androidx.lifecycle.ViewModel
import com.gab.model_module.usecases.AddToPlaylistUseCase
import com.gab.model_module.usecases.ChangePlaylistUseCase
import com.gab.model_module.usecases.CreatePlaylistUseCase
import com.gab.model_module.usecases.DecrementDurationUseCase
import com.gab.model_module.usecases.GetMinDurationInSecondsUseCase
import com.gab.model_module.usecases.GetPlaylistsUseCase
import com.gab.model_module.usecases.GetTracksWithoutDurationFilterUseCase
import com.gab.model_module.usecases.GetTracksWithDurationFilterUseCase
import com.gab.model_module.usecases.IncrementDurationUseCase
import com.gab.model_module.usecases.IsDarkThemeChangeUseCase
import com.gab.model_module.usecases.IsDarkThemeUseCase
import com.gab.model_module.usecases.RemoveFromPlaylistUseCase
import com.gab.model_module.usecases.RemovePlaylistUseCase
import com.gab.model_module.usecases.SetPlaylistPictureUseCase
import com.gab.model_module.usecases.UpdateUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val addToPlaylistUseCase: AddToPlaylistUseCase,
    private val changePlaylistUseCase: ChangePlaylistUseCase,
    private val createPlaylistUseCase: CreatePlaylistUseCase,
    private val getPlaylistsUseCase: GetPlaylistsUseCase,
    private val removeFromPlaylistUseCase: RemoveFromPlaylistUseCase,
    private val removePlaylistUseCase: RemovePlaylistUseCase,
    private val setPlaylistPictureUseCase: SetPlaylistPictureUseCase,
    private val getTracksWithDurationFilterUseCase: GetTracksWithDurationFilterUseCase,
    private val getTracksWithoutDurationFilterUseCase: GetTracksWithoutDurationFilterUseCase,
    private val getMinDurationFlowUseCase: GetMinDurationInSecondsUseCase,
    private val incrementDurationUseCase: IncrementDurationUseCase,
    private val decrementDurationUseCase: DecrementDurationUseCase,
    private val isDarkThemeUseCase: IsDarkThemeUseCase,
    private val isDarkThemeChangeUseCase: IsDarkThemeChangeUseCase,
    private val updateUseCase: UpdateUseCase,
) : ViewModel()
//{
//
//    init {
//        viewModelScope.launch {
//            GAB_CHECK("PlaylistsViewModel initialized")
//        }
//    }
//    fun update() {
//        viewModelScope.launch { updateUseCase() }
//    }
//    val isThemeDark = isDarkThemeUseCase()
//    fun isDarkThemeChange() {
//        viewModelScope.launch {
//            isDarkThemeChangeUseCase()
//        }
//    }
//
//    fun getMinDurationFlow() = getMinDurationFlowUseCase()
//    fun incrementMinDuration() {
//        viewModelScope.launch {
//            incrementDurationUseCase()
//        }
//    }
//
//    fun decrementMinDuration() {
//        viewModelScope.launch {
//            decrementDurationUseCase()
//        }
//    }
//
//    val tracks = getTracksUseCaseWithDurationFilter().map {
//        AllTracksScreenState.Tracks(it) as AllTracksScreenState
//    }.onStart { emit(AllTracksScreenState.DataIsLoading) }
//
//    val tracksWithoutDurationFilter = getTracksUseCaseWithoutDurationFilter().map {
//        AllTracksScreenState.Tracks(it) as AllTracksScreenState
//    }.onStart { emit(AllTracksScreenState.DataIsLoading) }
//
//    val playlists = getPlaylistsUseCase().map {
//        PlaylistScreenState.Playlists(it) as PlaylistScreenState
//    }.onStart { emit(PlaylistScreenState.Loading) }
//
//    fun addToPlaylist(playlist: PlaylistInfoModel, trackInfoModel: TrackInfoModel) {
//        viewModelScope.launch {
//            addToPlaylistUseCase(playlist, trackInfoModel)
//        }
//    }
//
//    private val _playlistCreationOrEditingResultFlow = MutableSharedFlow<Boolean>()
//    val playlistCreationOrEditingResultFlow = flow {
//        _playlistCreationOrEditingResultFlow.collect { emit(it) }
//    }
//
//    fun changePlaylist(
//        playlistId: Long,
//        tracks: List<TrackInfoModel>,
//        title: String,
//        coverUri: Uri,
//    ) {
//        viewModelScope.launch {
//            _playlistCreationOrEditingResultFlow.emit(
//                changePlaylistUseCase(
//                    playlistId,
//                    tracks,
//                    title,
//                    coverUri
//                )
//            )
//
//        }
//    }
//
//    fun createPlaylist(tracks: List<TrackInfoModel>, title: String, coverUri: Uri) {
//        viewModelScope.launch {
//            _playlistCreationOrEditingResultFlow.emit(
//                createPlaylistUseCase(
//                    tracks = tracks,
//                    title = title,
//                    coverUri = coverUri
//                )
//            )
//        }
//    }
//
//    fun removeFromPlaylist(playlist: PlaylistInfoModel, trackInfoModel: TrackInfoModel) {
//        viewModelScope.launch {
//            removeFromPlaylistUseCase(playlist, trackInfoModel)
//        }
//    }
//
//    fun removePlaylist(playlist: PlaylistInfoModel) {
//        viewModelScope.launch {
//            removePlaylistUseCase(playlist)
//        }
//    }
//
//
//    override fun onCleared() {
//        GAB_CHECK("PlaylistsViewModel cleared")
//        super.onCleared()
//    }
//
//    fun setPlaylistPicture(uri: Uri, playlist: PlaylistInfoModel) {
//        viewModelScope.launch { setPlaylistPictureUseCase(playlist = playlist, uri = uri) }
//    }
//}