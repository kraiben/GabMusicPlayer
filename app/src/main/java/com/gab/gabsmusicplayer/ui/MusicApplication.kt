package com.gab.gabsmusicplayer.ui

import android.app.Application
import com.gab.gabsmusicplayer.di.ApplicationComponent
import com.gab.gabsmusicplayer.di.DaggerApplicationComponent

class MusicApplication : Application() {

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
//    val musicComponent: MusicServiceComponent by lazy {
//        DaggerMusicServiceComponent.factory().create(this)
//    }
}