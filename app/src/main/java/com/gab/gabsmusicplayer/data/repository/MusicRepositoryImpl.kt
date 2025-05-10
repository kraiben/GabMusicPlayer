package com.gab.gabsmusicplayer.data.repository

import android.content.ContentResolver
import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore
import com.gab.gabsmusicplayer.domain.models.TrackInfoModel
import com.gab.gabsmusicplayer.domain.repository.MusicRepository
import com.gab.gabsmusicplayer.utils.GAB_CHECK
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(
    private val contentResolver: ContentResolver,
//    dataStore: DataStore<Preferences>
) : MusicRepository {


    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val updateFlow = MutableSharedFlow<Unit>(replay = 1)

    private val tracks = flow<List<TrackInfoModel>> {
        updateFlow.emit(Unit)
        updateFlow.collect {
            emit(loadMusicFiles())
        }
    }
        .stateIn(coroutineScope, SharingStarted.Lazily, listOf())

    private fun loadMusicFiles(): List<TrackInfoModel> {

        GAB_CHECK("load started")
        val tracks = mutableListOf<TrackInfoModel>()
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ALBUM_ID
        )
        val sortOrder = MediaStore.Audio.Media.DATE_ADDED + " DESC"

        val selection = "${MediaStore.Audio.Media.MIME_TYPE} = ? OR ${MediaStore.Audio.Media.MIME_TYPE} = ?"
        val selectionArgs = arrayOf("audio/mpeg", "audio/wav")

        val cursor = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)

        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val titleColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val artistColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val pathColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
            val durationColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            val albumArtColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)

            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val title = it.getString(titleColumn)
                val artist = it.getString(artistColumn)
                val path = it.getString(pathColumn)
                val duration = it.getLong(durationColumn)
                val albumArtUri = ContentUris.withAppendedId(
                    Uri.parse("content://media/external/audio/albumart"),
                    it.getLong(albumArtColumn)
                )
                val musicFile = TrackInfoModel(id, title, artist, path, duration, albumArtUri)
                tracks.add(musicFile)
            }
        }
        GAB_CHECK("load finished")
        return tracks.filter { it.duration >= MIN_DURATION }
    }

    suspend fun update() {
        updateFlow.emit(Unit)
    }
    fun getTracks(): StateFlow<List<TrackInfoModel>> = tracks

    companion object {
//        val MIN_TRACK_DURATION_KEY = preferencesKey("")
//        val MIN_TRACK_DURATION_KEY = ("MIN_TRACK_DURATION")
        val MIN_DURATION = 90 * 1000L

    }
}