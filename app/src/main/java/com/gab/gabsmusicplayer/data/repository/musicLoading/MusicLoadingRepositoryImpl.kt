package com.gab.gabsmusicplayer.data.repository.musicLoading

import android.content.ContentResolver
import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore
import com.gab.gabsmusicplayer.R
import com.gab.gabsmusicplayer.di.ApplicationScope
import com.gab.gabsmusicplayer.domain.models.PlaylistInfoModel
import com.gab.gabsmusicplayer.domain.models.TrackInfoModel
import com.gab.gabsmusicplayer.domain.repository.MusicLoadingRepository
import com.gab.gabsmusicplayer.utils.GAB_CHECK
import com.gab.gabsmusicplayer.utils.mergeWith
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.Date
import java.util.Locale
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject

@ApplicationScope
class MusicLoadingRepositoryImpl @Inject constructor(
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
            GAB_CHECK("playlistsFlowNeedUpdate changed. Thread: ${Thread.currentThread().name}")
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
        .stateIn(coroutineScope, SharingStarted.Eagerly, emptyList())

    private val _tracks = mutableListOf<TrackInfoModel>()
    private val tracks: List<TrackInfoModel>
        get() = _tracks.toList()
    private val tracksChanges = MutableSharedFlow<List<TrackInfoModel>>()
    private val tracksFlowNeedUpdate = MutableSharedFlow<Unit>(replay = 1)
    private val tracksLoadingFlow: Flow<List<TrackInfoModel>> = flow {
        tracksFlowNeedUpdate.emit(Unit)
        tracksFlowNeedUpdate.collect {
            _tracks.clear()
            _tracks.addAll(loadMusicFiles())
            emit(tracks)
        }
    }
    private val tracksFlow =
        tracksChanges.mergeWith(tracksLoadingFlow)
            .stateIn(coroutineScope, SharingStarted.Eagerly, emptyList())

    init {
        val ps1  = getPlaylistsFiles()
        val ps2 = getPlaylistsImageFiles()

//        ps1.forEach {
//            it.delete()
//        }
//        ps2.forEach {
//            it.delete()
//        }
//
//        ps1.forEach {
//            GAB_CHECK(it)
//        }
//        ps2.forEach {
//            GAB_CHECK(it)
//        }

    }

    override fun getPlaylists(): StateFlow<List<PlaylistInfoModel>> = playlistsFlow
    override fun getAllTracks(): StateFlow<List<TrackInfoModel>> = tracksFlow

    override suspend fun isTitleUniqueForCreation(title: String): Boolean {
        return !playlists.any { it.title == title }
    }

    override suspend fun isTitleUniqueForEdit(playlistId: Long, title: String ): Boolean {
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
                    Uri.EMPTY
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
        return Uri.fromFile(imageFile)
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

    private fun readPlaylistFromM3U(file: File): PlaylistInfoModel {
        val tracks = mutableListOf<TrackInfoModel>()
        var uri: Uri = Uri.EMPTY
        var createdAt = Date()
        withFileLock(file) {
            try {
                file.bufferedReader().use { f ->
                    if (f.readLine() != PLAYLIST_FILE_HEADER) throw InterruptedException("Неверно записан плейлист")
                    val _uri = f.readLine()
                    if (_uri != "") uri = Uri.parse(_uri)
                    createdAt = try {
                        Date(f.readLine().toLong())
                    } catch (_: Exception) {
                        Date()
                    }
                    while (f.readLine() == PLAYLIST_FILE_TRACK_INFO_HEADER) {
                        val title = f.readLine()
                        val path = f.readLine()
                        tracks.addAll(
                            loadMusicFileByTitleAndPath(title, path)
                        )
                    }
                }
            } catch (e: Exception) {
                file.delete()
                GAB_CHECK("READING PLAYLIST ERROR")
                return@withFileLock PlaylistInfoModel.EMPTY
            }
        }

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

    private fun writePlaylistIntoM3U(playlist: PlaylistInfoModel) {
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
                println("Ошибка при записи в файл: ${e.message}")
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
    private fun loadMusicFileByTitleAndPath(
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

    private fun loadMusicFiles(): List<TrackInfoModel> {
        GAB_CHECK("load started")
        val selectionArgs = arrayOf<String>()
        val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"
        return getTracksFormMediaStore(selection, selectionArgs)
    }

    private fun getTracksFormMediaStore(
        selection: String,
        selectionArgs: Array<String>,
    ): MutableList<TrackInfoModel> {
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
                        Uri.parse("content://media/external/audio/albumart"),
                        it.getLong(albumArtColumn)
                    )
                val albumUriPatch = getFallbackAlbumCover()
                val musicFile =
                    TrackInfoModel(
                        id,
                        title,
                        artist,
                        path,
                        duration,
                        albumArtUri,
                        albumUriPatch,
                        dateAdded
                    )
                tracks.add(musicFile)
            }
        }

        return tracks
    }

    companion object {
        private val locks = ConcurrentHashMap<String, Any>()

        fun <T> withFileLock(file: File, action: () -> T): T {
            val lock = locks.getOrPut(file.absolutePath) { Any() }
            return synchronized(lock) {
                action()
            }
        }

        @Volatile
        private var autoincrementId = AtomicLong(0)
        fun getNewId(): Long {
            return autoincrementId.getAndIncrement()
        }

        @Volatile
        private var coverPatchesUsed = 0

        private val coverPatchesList = listOf(
            R.drawable.gpic_cg_boat2,
            R.drawable.gpic_cg_dream1_1,
            R.drawable.gpic_cg_dream3_3,
            R.drawable.gpic_from_niko,
            R.drawable.gpic_megumindk,
            R.drawable.gpic_miku_headphones,
            R.drawable.gpic_sol_flight2,
            R.drawable.gpic_sol_flight3,
            R.drawable.gpic_sol_glenboat2,
            R.drawable.gpic_sol_glenboat4,
        )

        private const val PLAYLIST_DIR_NAME = "playlists"
        private const val PLAYLIST_IMAGES_DIR_NAME = "playlists_images"
        private const val PLAYLIST_FILE_HEADER = "#EXTM3U"
        private const val PLAYLIST_FILE_TRACK_INFO_HEADER = "#EXTINF"

        private fun getFallbackAlbumCover() =
            coverPatchesList[(coverPatchesUsed++) % coverPatchesList.size]
    }
}