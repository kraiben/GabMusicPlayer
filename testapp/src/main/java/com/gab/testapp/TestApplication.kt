package com.gab.testapp

import android.app.Application
import com.gab.core_media.di.CoreMediaDependenciesStore
import com.gab.testapp.di.DaggerTestAppComponent
import com.gab.testapp.di.TestAppComponent

class TestApplication: Application() {

    val component: TestAppComponent by lazy {
        DaggerTestAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        CoreMediaDependenciesStore.deps = component
    }

}