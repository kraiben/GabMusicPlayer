plugins {
    id("android-library-convention")
    id("android-compose-convention")
}

android {
    namespace = "com.gab.feature_api_all_tracks"
}

dependencies {
    implementation(projects.musicEntitiesModule)
}