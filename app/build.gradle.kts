plugins {
    id("android-application-convention")
    id("kotlin-kapt-convention")
    id("kotlin-serialization-convention")
}

android {
    namespace = "com.gab.gabsmusicplayer"

    defaultConfig {
        applicationId = "com.gab.gabsmusicplayer"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {

        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {

    implementation(projects.coreMusicLoading)
    implementation(projects.coreSettings)
    implementation(projects.coreMedia)
    implementation(projects.modelModule)
    implementation(projects.musicEntitiesModule)
    implementation(projects.modelMediaUsecases)
    implementation(projects.featureAllTracks)
    implementation(projects.featureOptionsMenus)
    implementation(projects.featurePlaylists)

    implementation(libs.datastore.preferences)

    implementation(libs.firebase.crashlytics.buildtools)

    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.exoplayer.dash)
    implementation(libs.androidx.media3.ui)
    implementation(libs.androidx.media3.session)
    implementation(libs.androidx.media)
    implementation(libs.accompanist.permissions)
}