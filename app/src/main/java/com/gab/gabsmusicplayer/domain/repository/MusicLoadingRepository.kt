package com.gab.gabsmusicplayer.domain.repository

import android.net.Uri
import com.gab.gabsmusicplayer.domain.models.PlaylistInfoModel
import com.gab.gabsmusicplayer.domain.models.TrackInfoModel
import kotlinx.coroutines.flow.StateFlow

interface MusicLoadingRepository {
    fun getAllTracks(): StateFlow<List<TrackInfoModel>>
    fun getPlaylists(): StateFlow<List<PlaylistInfoModel>>

    suspend fun update()
    suspend fun addToPlaylist(playlist: PlaylistInfoModel, track: TrackInfoModel)
    suspend fun removeFromPlaylist(playlist: PlaylistInfoModel, track: TrackInfoModel)

    suspend fun createPlaylist(tracks: List<TrackInfoModel>, title: String, coverUri: Uri)
    suspend fun changePlaylistInfo(playlistId: Long, tracks: List<TrackInfoModel>, title: String, coverUri: Uri)
    suspend fun removePlaylist(playlist: PlaylistInfoModel)

    suspend fun setPlaylistPicture(playlist: PlaylistInfoModel, uri: Uri)

    suspend fun isTitleUniqueForCreation(title: String): Boolean
    suspend fun isTitleUniqueForEdit(playlistId: Long, title: String): Boolean
}