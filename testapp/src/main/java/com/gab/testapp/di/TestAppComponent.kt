package com.gab.testapp.di

import android.content.Context
import com.gab.core_media.di.CoreMediaDependencies
import com.gab.testapp.MainActivity
import dagger.BindsInstance
import dagger.Component

@Component(modules = [TestAppModule::class])
interface TestAppComponent: CoreMediaDependencies {
    fun inject(activity: MainActivity)
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): TestAppComponent
    }

}