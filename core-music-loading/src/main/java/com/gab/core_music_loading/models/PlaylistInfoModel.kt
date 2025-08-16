package com.gab.core_music_loading.models

import android.net.Uri
import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import androidx.core.net.toUri
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.util.Date

@Keep
@Immutable
public data class PlaylistInfoModel(
    val id: Long,
    val title: String,
    val tracks: List<TrackInfoModel>,
    val coverUri: Uri,
    val fallbackCover: Int,
    val createdAt: Date,
    ) {
    public fun toJson(): String = gson.toJson(this)
    public companion object {
        public val EMPTY: PlaylistInfoModel = PlaylistInfoModel(-1, "", emptyList(), Uri.EMPTY, fallbackCover = -1,
            Date()
        )

        private object UriTypeAdapter : TypeAdapter<Uri>() {
            override fun write(out: JsonWriter, value: Uri) {
                out.value(value.toString())
            }

            override fun read(reader: JsonReader): Uri {
                return reader.nextString().toUri()
            }
        }
        private object DateTypeAdapter : TypeAdapter<Date>() {
            override fun write(out: JsonWriter, value: Date) {
                out.value(value.time)
            }

            override fun read(reader: JsonReader): Date {
                return Date(reader.nextLong())
            }
        }

        private val gson = GsonBuilder()
            .registerTypeAdapter(Uri::class.java, UriTypeAdapter)
            .registerTypeAdapter(Date::class.java, DateTypeAdapter)
            .create()
        public fun fromJson(str: String): PlaylistInfoModel {
            return gson.fromJson(str, PlaylistInfoModel::class.java)
        }
    }
}