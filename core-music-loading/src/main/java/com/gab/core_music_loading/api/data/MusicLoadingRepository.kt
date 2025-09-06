package com.gab.core_music_loading.api.data

import android.net.Uri
import com.gab.music_entities_module.PlaylistInfoModel
import com.gab.music_entities_module.TrackInfoModel
import kotlinx.coroutines.flow.StateFlow

public interface MusicLoadingRepository {
    public fun getAllTracks(): StateFlow<List<TrackInfoModel>>
    public fun getPlaylists(): StateFlow<List<PlaylistInfoModel>>

    public suspend fun update()
    public suspend fun addToPlaylist(playlist: PlaylistInfoModel, track: TrackInfoModel)
    public suspend fun removeFromPlaylist(playlist: PlaylistInfoModel, track: TrackInfoModel)

    public suspend fun createPlaylist(tracks: List<TrackInfoModel>, title: String, coverUri: Uri)
    public suspend fun changePlaylistInfo(playlistId: Long, tracks: List<TrackInfoModel>, title: String, coverUri: Uri)
    public suspend fun removePlaylist(playlist: PlaylistInfoModel)

    public suspend fun setPlaylistPicture(playlist: PlaylistInfoModel, uri: Uri)

    public suspend fun isTitleUniqueForCreation(title: String): Boolean
    public suspend fun isTitleUniqueForEdit(playlistId: Long, title: String): Boolean
}