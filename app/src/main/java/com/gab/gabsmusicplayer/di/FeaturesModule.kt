package com.gab.gabsmusicplayer.di

import com.gab.feature_all_tracks.di.AllTracksFeatureComponent
import com.gab.feature_all_tracks.holder.AllTTracksFeatureHolder
import dagger.Module
import dagger.Provides

@Module
class FeaturesModule {

    @Provides
    fun provideAllTracksFeatureHolder(): AllTTracksFeatureHolder {
        return AllTracksFeatureComponent.get().getHolder()
    }

}