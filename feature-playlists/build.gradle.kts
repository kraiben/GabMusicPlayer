plugins {
    id("android-library-convention")
    id("kotlin-kapt-convention")
    id("android-compose-convention")

}

android  {
    namespace = "com.gab.feature_playlists"
}

dependencies {
    implementation(projects.musicEntitiesModule)
    implementation(projects.modelMediaUsecases)
    implementation(projects.modelModule)
}