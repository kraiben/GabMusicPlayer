package com.gab.core_music_loading.impl.data

import android.content.ContentResolver
import android.net.Uri
import com.gab.core_music_loading.api.data.MusicLoadingRepository
import com.gab.core_music_loading.impl.data.room.entities.database.DatabaseMapper
import com.gab.core_music_loading.impl.data.room.entities.database.MusicDao
import com.gab.core_music_loading.impl.data.room.entities.database.PictureSelector
import com.gab.music_entities_module.PlaylistInfoModel
import com.gab.music_entities_module.TrackInfoModel
import kotlinx.coroutines.flow.StateFlow
import java.io.File
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject

internal class MusicLoadingRepositoryImpl @Inject constructor(
    private val contentResolver: ContentResolver,
    private val fileDirectory: File,
    private val mapper: DatabaseMapper,
    private val dao: MusicDao
) : MusicLoadingRepository {

    override fun getAllTracks(): StateFlow<List<TrackInfoModel>> {
        TODO("Not yet implemented")
    }

    override fun getPlaylists(): StateFlow<List<PlaylistInfoModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun update() {
        TODO("Not yet implemented")
    }

    override suspend fun addToPlaylist(
        playlist: PlaylistInfoModel,
        track: TrackInfoModel,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun removeFromPlaylist(
        playlist: PlaylistInfoModel,
        track: TrackInfoModel,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun createPlaylist(
        tracks: List<TrackInfoModel>,
        title: String,
        coverUri: Uri,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun changePlaylistInfo(
        playlistId: Long,
        tracks: List<TrackInfoModel>,
        title: String,
        coverUri: Uri,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun removePlaylist(playlist: PlaylistInfoModel) {
        TODO("Not yet implemented")
    }

    override suspend fun setPlaylistPicture(
        playlist: PlaylistInfoModel,
        uri: Uri,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun isTitleUniqueForCreation(title: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun isTitleUniqueForEdit(
        playlistId: Long,
        title: String,
    ): Boolean {
        TODO("Not yet implemented")
    }
    private fun getFallbackCover(): Int {
        return PictureSelector.getPic()
    }
    private suspend fun isRightUri(uri: Uri): Boolean {
        try {
            val ist = contentResolver.openInputStream(uri)
            ist?.close()
            return ist != null
        } catch (_: Exception) {
            return false
        }
    }
    companion object {
        private var autoincrementId = AtomicLong(0)
        fun getNewId(): Long {
            return autoincrementId.getAndIncrement()
        }
    }
}