package com.gab.core_music_loading.impl.di

import android.content.ContentResolver
import androidx.annotation.RestrictTo
import com.gab.core_music_loading.api.di.MusicLoadingApi
import dagger.Component
import java.io.File
import kotlin.properties.Delegates.notNull

@Component(modules = [MusicLoadingModule::class], dependencies = [CoreMusicLoadingDependencies::class])
@MusicLoadingScope
public abstract class CoreMusicLoadingComponent: MusicLoadingApi{

    public companion object {
        private var instance: CoreMusicLoadingComponent? = null

        public fun get(): CoreMusicLoadingComponent {
            if (instance == null) {
                synchronized(CoreMusicLoadingComponent::class.java) {
                    if (instance == null) {
                        instance = DaggerCoreMusicLoadingComponent.factory().create(
                            CoreMusicLoadingDependenciesProvider.deps
                        )
                    }
                }
            }
            return instance!!
        }
    }

    @Component.Factory
    public interface Factory {
        public fun create(
            deps: CoreMusicLoadingDependencies,
        ): CoreMusicLoadingComponent
    }
}

public interface CoreMusicLoadingDependencies {

    public val contentResolver: ContentResolver

    public val fileDirectory: File
}

public interface CoreMusicLoadingDependenciesProvider {

    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    public val deps: CoreMusicLoadingDependencies

    public companion object: CoreMusicLoadingDependenciesProvider by CoreMusicLoadingDependenciesStore

}

public object CoreMusicLoadingDependenciesStore: CoreMusicLoadingDependenciesProvider {
    override var deps: CoreMusicLoadingDependencies by notNull()
}

