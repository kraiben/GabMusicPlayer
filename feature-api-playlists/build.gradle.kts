plugins {
    id("android-library-convention")
    id("android-compose-convention")
}

android {
    namespace = "com.gab.feature_api_playlists"
}

dependencies {

    implementation(projects.musicEntitiesModule)
}