package com.gab.core_music_loading

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge

internal fun <T> Flow<T>.mergeWith(anotherFlow: Flow<T>): Flow<T> {
    return merge(this, anotherFlow)
}
