plugins {
    id("android-library-convention")
    id("android-compose-convention")
    id("kotlin-kapt-convention")
}

android {
    namespace = "com.gab.feature_all_tracks"
}

dependencies {
    implementation(projects.musicEntitiesModule)
    implementation(projects.modelMediaUsecases)
    implementation(projects.modelModule)
}