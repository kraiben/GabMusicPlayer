package com.gab.core_music_loading.impl.data.room.entities.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.gab.core_music_loading.impl.data.room.entities.PlaylistInfoEntity
import com.gab.core_music_loading.impl.data.room.entities.PlaylistWithTracks
import com.gab.core_music_loading.impl.data.room.entities.TrackInfoEntity
import com.gab.core_music_loading.impl.data.room.entities.TrackToPlaylistRelation

@Dao
public interface MusicDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public suspend fun insertPlaylist(playlist: PlaylistInfoEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public suspend fun insertTrack(track: TrackInfoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public suspend fun addTrackToPlaylist(crossRefs: List<TrackToPlaylistRelation>)

    @Transaction
    @Query("SELECT * FROM tracks WHERE id = :trackId")
    public suspend fun getTrack(trackId: Long): TrackInfoEntity?

    @Transaction
    @Query("SELECT * FROM playlists_short")
    public suspend fun getPlaylistWithTracks(): List<PlaylistWithTracks>

    @Query("DELETE FROM playlists_short WHERE id = :playlistId")
    public suspend fun deletePlaylist(playlistId: Long)

    @Query("DELETE FROM track_to_playlist_relations WHERE trackId = :trackId AND playlistId = :playlistId")
    public suspend fun deleteTrackFromPlaylist(trackId: Long, playlistId: Long)
}