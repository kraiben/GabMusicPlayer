package com.gab.gabsmusicplayer.utils

import android.net.Uri

fun String.encode(): String {
    return Uri.encode(this)
}


