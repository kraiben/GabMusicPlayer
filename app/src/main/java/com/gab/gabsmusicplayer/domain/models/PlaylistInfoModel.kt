package com.gab.gabsmusicplayer.domain.models

import android.net.Uri
import com.gab.gabsmusicplayer.R
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.util.Date

data class PlaylistInfoModel(
    val id: Long,
    val title: String,
    val tracks: List<TrackInfoModel>,
    val coverUri: Uri,
    val fallbackCover: Int,
    val createdAt: Date


) {
    fun toJson() = gson.toJson(this)
    companion object {
        val EMPTY = PlaylistInfoModel(-1, "", emptyList(), Uri.EMPTY, fallbackCover = R.drawable.gpic_megumindk, Date())

        object UriTypeAdapter : TypeAdapter<Uri>() {
            override fun write(out: JsonWriter, value: Uri) {
                out.value(value.toString())
            }

            override fun read(`in`: JsonReader): Uri {
                return Uri.parse(`in`.nextString())
            }
        }
        private val gson = GsonBuilder()
            .registerTypeAdapter(Uri::class.java, UriTypeAdapter)
            .create()
        fun fromJson(str: String): PlaylistInfoModel {
            return gson.fromJson(str, PlaylistInfoModel::class.java)
        }
    }
}
