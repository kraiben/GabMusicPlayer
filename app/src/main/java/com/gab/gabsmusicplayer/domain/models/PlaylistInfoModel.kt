package com.gab.gabsmusicplayer.domain.models

import android.net.Uri
import androidx.annotation.Keep
import androidx.core.net.toUri
import com.gab.gabsmusicplayer.R
import com.gab.gabsmusicplayer.utils.GAB_CHECK
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.util.Date

@Keep
data class PlaylistInfoModel(
    val id: Long,
    val title: String,
    val tracks: List<TrackInfoModel>,
    val coverUri: Uri,
    val fallbackCover: Int,
    val createdAt: Date,


    ) {
    fun toJson(): String = gson.toJson(this)
    companion object {
        val EMPTY = PlaylistInfoModel(-1, "", emptyList(), Uri.EMPTY, fallbackCover = R.drawable.gpic_megumindk, Date())

        object UriTypeAdapter : TypeAdapter<Uri>() {
            override fun write(out: JsonWriter, value: Uri) {
                out.value(value.toString())
            }

            override fun read(reader: JsonReader): Uri {
                return reader.nextString().toUri()
            }
        }
        object DateTypeAdapter : TypeAdapter<Date>() {
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
        fun fromJson(str: String): PlaylistInfoModel {

            GAB_CHECK(gson)
            return gson.fromJson(str, PlaylistInfoModel::class.java)
        }
    }
}
