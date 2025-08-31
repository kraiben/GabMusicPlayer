package com.gab.core_music_loading.impl.data

import android.content.ContentResolver
import android.content.ContentUris
import android.net.Uri
import android.net.Uri.EMPTY
import android.net.Uri.fromFile
import android.provider.MediaStore
import androidx.core.net.toUri
import com.gab.core_music_loading.GAB_CHECK
import com.gab.core_music_loading.R
import com.gab.core_music_loading.api.data.MusicLoadingRepository
import com.gab.core_music_loading.mergeWith
import com.gab.music_entities_module.TrackInfoModel
import com.gab.music_entities_module.PlaylistInfoModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.Date
import java.util.Locale
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject
import kotlin.collections.plus
import kotlin.system.measureTimeMillis

internal class MusicLoadingRepositoryImpl @Inject constructor(
    private val contentResolver: ContentResolver,
    private val fileDirectory: File,
) : MusicLoadingRepository {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val playlistDirectory = initPlaylistsDirectory()
    private val playlistImagesDirectory = initPlaylistImagesDirectory()
    private val _playlists = mutableListOf<PlaylistInfoModel>()
    private val playlists: List<PlaylistInfoModel>
        get() = _playlists.toList()
    private val playlistsChanges = MutableSharedFlow<List<PlaylistInfoModel>>()
    private val playlistsFlowNeedUpdate = MutableSharedFlow<Unit>(replay = 1)
    private val playlistsLoadingFlow: Flow<List<PlaylistInfoModel>> = flow {
        playlistsFlowNeedUpdate.emit(Unit)
        playlistsFlowNeedUpdate.collect {
            _playlists.clear()
            _playlists.addAll(
                getPlaylistsFiles().map {
                    readPlaylistFromM3U(it)
                }
            )
            emit(playlists)
        }
    }
    private val playlistsFlow = playlistsChanges.mergeWith(playlistsLoadingFlow)
        .stateIn(coroutineScope, SharingStarted.Lazily, emptyList())

    private val _tracks = mutableListOf<TrackInfoModel>()
    private val tracks: List<TrackInfoModel>
        get() = _tracks.toList()
    private val tracksChanges = MutableSharedFlow<List<TrackInfoModel>>()
    private val tracksFlowNeedUpdate = MutableSharedFlow<Unit>(replay = 1)
    private val tracksLoadingFlow: Flow<List<TrackInfoModel>> = flow {
        tracksFlowNeedUpdate.emit(Unit)
        tracksFlowNeedUpdate.collect {
            _tracks.clear()
            val time = measureTimeMillis {
                _tracks.addAll(loadMusicFiles())
            }
            GAB_CHECK(time)
            emit(tracks)
        }
    }
    private val tracksFlow =
        tracksChanges.mergeWith(tracksLoadingFlow)
            .stateIn(coroutineScope, SharingStarted.Lazily, emptyList())

    override suspend fun update() {
        tracksFlowNeedUpdate.emit(Unit)
        playlistsFlowNeedUpdate.emit(Unit)
    }

    override fun getPlaylists(): StateFlow<List<PlaylistInfoModel>> = playlistsFlow
    override fun getAllTracks(): StateFlow<List<TrackInfoModel>> = tracksFlow

    override suspend fun isTitleUniqueForCreation(title: String): Boolean {
        return !playlists.any { it.title == title }
    }

    override suspend fun isTitleUniqueForEdit(playlistId: Long, title: String): Boolean {
        return !playlists.any {
            (it.title == title) && (it.id != playlistId)
        }
    }

    override suspend fun addToPlaylist(playlist: PlaylistInfoModel, track: TrackInfoModel) {
        if (playlist.tracks.any { it.title == track.title }) return
        val index = playlists.indexOfFirst { it.title == playlist.title }
        val p = playlist.copy(
            tracks = playlist.tracks + track
        )
        _playlists[index] = p
        playlistsChanges.emit(playlists)
        coroutineScope.launch {
            writePlaylistIntoM3U(p)
        }
    }

    override suspend fun removeFromPlaylist(playlist: PlaylistInfoModel, track: TrackInfoModel) {
        val index = playlists.indexOfFirst { it.title == playlist.title }
        val p = playlist.copy(
            tracks = playlist.tracks.filter { it.title != track.title }
        )
        _playlists[index] = p
        playlistsChanges.emit(playlists)
        coroutineScope.launch {
            writePlaylistIntoM3U(p)
        }
    }

    override suspend fun createPlaylist(
        tracks: List<TrackInfoModel>,
        title: String,
        coverUri: Uri,
    ) {
        try {
            val playlist = PlaylistInfoModel(
                id = getNewId(),
                tracks = tracks,
                title = title,
                coverUri = try {
                    copyImageToLocalFile(title = title, uri = coverUri)
                } catch (_: Exception) {
                    EMPTY
                },
                fallbackCover = getFallbackAlbumCover(),
                createdAt = Date()
            )
            _playlists.add(playlist)
            playlistsChanges.emit(playlists)
            coroutineScope.launch {
                writePlaylistIntoM3U(playlist)
            }
        } catch (e: Exception) {
            GAB_CHECK("Playlist creation error ${e.message}")
        }
    }

    override suspend fun changePlaylistInfo(
        playlistId: Long,
        tracks: List<TrackInfoModel>,
        title: String,
        coverUri: Uri,
    ) {
        val index = playlists.indexOfFirst { it.id == playlistId }
        val oldPlaylistInfo = playlists[index]
        val newPlaylistInfo = oldPlaylistInfo.copy(
            title = title,
            tracks = tracks,
            coverUri = copyImageToLocalFile(title, coverUri)
        )
        coroutineScope.launch {
            writePlaylistIntoM3U(newPlaylistInfo)
            if (title != oldPlaylistInfo.title) {
                removePlaylistM3UFile(oldPlaylistInfo)
            }
        }
        _playlists[index] = newPlaylistInfo
        playlistsChanges.emit(playlists)
    }

    override suspend fun removePlaylist(playlist: PlaylistInfoModel) {
        _playlists.removeIf { playlist.title == it.title }
        playlistsChanges.emit(playlists)
        coroutineScope.launch {
            removePlaylistM3UFile(playlist)
        }
    }

    private fun getPlaylistImageFile(title: String) =
        File(playlistImagesDirectory, "${title}_cover.jpg").apply {
            if (!exists()) createNewFile()
        }

    private fun getPlaylistsImageFiles(): Array<out File> {
        if (playlistImagesDirectory.exists() && playlistImagesDirectory.isDirectory) {
            return playlistImagesDirectory.listFiles() ?: emptyArray()
        } else {
            throw RuntimeException("Playlist Directory has not been initialized")
        }
    }

    private fun copyImageToLocalFile(title: String, uri: Uri): Uri {
        val imageFile = getPlaylistImageFile(title)
        if (imageFile.absolutePath == uri.path) return uri

        val inputStream = try {
            contentResolver.openInputStream(uri)
        } catch (e: FileNotFoundException) {
            return uri
        }
        inputStream?.use { input ->
            FileOutputStream(imageFile).use { output ->
                input.copyTo(output)
            }
        }
        return fromFile(imageFile)
    }

    override suspend fun setPlaylistPicture(playlist: PlaylistInfoModel, uri: Uri) {
        try {
            val index = playlists.indexOfFirst { it.title == playlist.title }
            val p = playlist.copy(
                coverUri = copyImageToLocalFile(playlist.title, uri)
            )
            _playlists[index] = p
            playlistsChanges.emit(playlists)
            coroutineScope.launch {
                writePlaylistIntoM3U(p)
            }
        } catch (e: Exception) {
            GAB_CHECK("Error saving image")
        }

    }

    private suspend fun readPlaylistFromM3U(file: File): PlaylistInfoModel {
        val tracks = mutableListOf<TrackInfoModel>()
        var uri: Uri = EMPTY
        var createdAt = Date()
        var tracksAddJob: Job? = null
        withFileLock(file) {
            try {
                file.bufferedReader().use { f ->
                    if (f.readLine() != PLAYLIST_FILE_HEADER) throw InterruptedException("Неверно записан плейлист")
                    val _uri = f.readLine()
                    if (_uri != "") uri = _uri.toUri()
                    createdAt = try {
                        Date(f.readLine().toLong())
                    } catch (_: Exception) {
                        Date()
                    }
                    while (f.readLine() == PLAYLIST_FILE_TRACK_INFO_HEADER) {
                        val title = f.readLine()
                        val path = f.readLine()
                        tracksAddJob = coroutineScope.launch {
                            tracks.addAll(
                                loadMusicFileByTitleAndPath(title, path)
                            )
                        }

                    }
                }
            } catch (e: Exception) {
                file.delete()
                GAB_CHECK("READING PLAYLIST ERROR")
                return@withFileLock PlaylistInfoModel.EMPTY
            }
        }
        tracksAddJob?.join()
        val playlist = PlaylistInfoModel(
            getNewId(),
            file.nameWithoutExtension,
            tracks.toList(),
            coverUri = uri,
            fallbackCover = getFallbackAlbumCover(),
            createdAt = createdAt
        )
        coroutineScope.launch { writePlaylistIntoM3U(playlist) }
        return playlist
    }

    private suspend fun writePlaylistIntoM3U(playlist: PlaylistInfoModel) {
        val file = getOrCreatePlaylistFile(playlist)
        val tempFile = File("${file.path}.tmp")
        withFileLock(file) {
            try {
                tempFile.bufferedWriter().use { f ->
                    f.write(PLAYLIST_FILE_HEADER)
                    f.newLine()
                    f.write(playlist.coverUri.path)
                    f.newLine()
                    f.write(playlist.createdAt.time.toString())
                    playlist.tracks.forEach { track ->
                        f.newLine()
                        f.write(PLAYLIST_FILE_TRACK_INFO_HEADER)
                        f.newLine()
                        f.write(track.title)
                        f.newLine()
                        f.write(track.path)
                    }
                }
                if (!tempFile.renameTo(file)) {
                    tempFile.delete()
                    throw IOException("Не удалось переименовать временный файл в целевой.")
                }
            } catch (e: IOException) {
                if (tempFile.exists()) {
                    tempFile.delete()
                }
                throw e
            }
        }
    }

    private fun getPlaylistsFiles(): Array<out File> {
        if (playlistDirectory.exists() && playlistDirectory.isDirectory) {
            return playlistDirectory.listFiles { pathname ->
                pathname?.name?.lowercase(
                    Locale.ENGLISH
                )?.endsWith(".m3u") ?: false
            } ?: emptyArray()
        } else {
            throw RuntimeException("Playlist Directory has not been initialized")
        }
    }

    private fun initPlaylistsDirectory(): File {
        val directory = File(fileDirectory, PLAYLIST_DIR_NAME)
        if (!directory.exists()) {
            directory.mkdirs()
        }
        return directory
    }

    private fun initPlaylistImagesDirectory(): File {
        val directory = File(fileDirectory, PLAYLIST_IMAGES_DIR_NAME)
        if (!directory.exists()) {
            directory.mkdirs()
        }
        return directory
    }

    private fun removePlaylistM3UFile(playlist: PlaylistInfoModel) {
        val file = getOrCreatePlaylistFile(playlist)
        file.delete()
        val image = getPlaylistImageFile(playlist.title)
        image.delete()
    }

    private fun getOrCreatePlaylistFile(playlist: PlaylistInfoModel): File {
        val file = File(playlistDirectory, "${playlist.title}.m3u")
        file.createNewFile()
        return file

    }

    private suspend fun loadMusicFileByTitleAndPath(
        titleToFind: String,
        pathToFind: String,
    ): List<TrackInfoModel> {
        val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0 " +
                "AND (${MediaStore.Audio.Media.TITLE} = ? " +
                "OR ${MediaStore.Audio.Media.DATA} = ?)"
        val selectionArgs = arrayOf(titleToFind, pathToFind)
        val tracks = getTracksFormMediaStore(selection, selectionArgs)
        return when (tracks.size) {
            1 -> tracks
            0 -> tracks
            else -> {
                var res = tracks.filter { it.path == pathToFind }
                if (res.isEmpty()) res = tracks.filter { it.title == titleToFind }
                res
            }
        }
    }

    private suspend fun loadMusicFiles(): List<TrackInfoModel> {
        GAB_CHECK("load started")
        val selectionArgs = arrayOf<String>()
        val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"
        return getTracksFormMediaStore(selection, selectionArgs)
    }

    private suspend fun getTracksFormMediaStore(
        selection: String,
        selectionArgs: Array<String>,
    ): MutableList<TrackInfoModel> = withContext(Dispatchers.IO) {
        val tracks = mutableListOf<TrackInfoModel>()
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.DATE_ADDED,
        )
        val sortOrder = MediaStore.Audio.Media.DATE_ADDED + " DESC"
        val cursor = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)
        val jobList = mutableListOf<Job>()
        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val titleColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val artistColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val pathColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
            val durationColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            val albumArtColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)
            val dateAddedColumn =
                it.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_ADDED)
            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val title = it.getString(titleColumn)
                val artist = it.getString(artistColumn)
                val path = it.getString(pathColumn)
                val duration = it.getLong(durationColumn)
                val dateAdded = Date(it.getLong(dateAddedColumn) * 1000)
                val albumArtUri =
                    ContentUris.withAppendedId(
                        "content://media/external/audio/albumart".toUri(),
                        it.getLong(albumArtColumn)
                    )
                jobList.add(coroutineScope.launch {
                    val albumUriPatch = getFallbackAlbumCover()
                    val musicFile =
                        TrackInfoModel(
                            id,
                            title,
                            artist,
                            path,
                            duration,
                            if (isRightUri(albumArtUri)) albumArtUri else EMPTY,
                            albumUriPatch,
                            dateAdded
                        )
                    tracks.add(musicFile)
                })
            }
        }
        jobList.forEach { it.join() }
        tracks
    }

    private fun isRightUri(uri: Uri): Boolean {
        try {
            val ist = contentResolver.openInputStream(uri)
            ist?.close()
            return ist != null
        } catch (_: Exception) {
            return false
        }
    }

    companion object {
        private val locks = ConcurrentHashMap<String, Any>()

        suspend fun <T> withFileLock(file: File, action: () -> T): T {
            val lock = locks.getOrPut(file.absolutePath) { Any() }
            return synchronized(lock) {
                action()
            }
        }

        private var autoincrementId = AtomicLong(0)
        fun getNewId(): Long {
            return autoincrementId.getAndIncrement()
        }
        private val coverPatchesList = listOf(
            R.drawable.gpic_1,
            R.drawable.gpic_2,
            R.drawable.gpic_3,
            R.drawable.gpic_4,
            R.drawable.gpic_5,
            R.drawable.gpic_6,
            R.drawable.gpic_7,
            R.drawable.gpic_8,
            R.drawable.gpic_9,
            R.drawable.gpic_10,
            R.drawable.gpic_11,
            R.drawable.gpic_12,
            R.drawable.gpic_13,
            R.drawable.gpic_14,
            R.drawable.gpic_15,
            R.drawable.gpic_16,
            R.drawable.gpic_17,
            R.drawable.gpic_18,
            R.drawable.gpic_19,
            R.drawable.gpic_20,
            R.drawable.gpic_21,
            R.drawable.gpic_22,
            R.drawable.gpic_23,
            R.drawable.gpic_24,
            R.drawable.gpic_25,
            R.drawable.gpic_26,
            R.drawable.gpic_27,
            R.drawable.gpic_28,
            R.drawable.gpic_29,
            R.drawable.gpic_30,
            R.drawable.gpic_31,
            R.drawable.gpic_32,
            R.drawable.gpic_33,
            R.drawable.gpic_34,
            R.drawable.gpic_35,
            R.drawable.gpic_36,
            R.drawable.gpic_39,
            R.drawable.gpic_40,
            R.drawable.gpic_41,
            R.drawable.gpic_42,
            R.drawable.gpic_43,
            R.drawable.gpic_44,
            R.drawable.gpic_45,
            R.drawable.gpic_46,
            R.drawable.gpic_47,
            R.drawable.gpic_48,
            R.drawable.gpic_49,
            R.drawable.gpic_50,
            R.drawable.gpic_51
        )

        private const val PLAYLIST_DIR_NAME = "playlists"
        private const val PLAYLIST_IMAGES_DIR_NAME = "playlists_images"
        private const val PLAYLIST_FILE_HEADER = "#EXTM3U"
        private const val PLAYLIST_FILE_TRACK_INFO_HEADER = "#EXTINF"

        private var picSelectedCnt = AtomicInteger(0)
        private fun getFallbackAlbumCover() =
            coverPatchesList[(picSelectedCnt.getAndIncrement()) % coverPatchesList.size]
    }
}