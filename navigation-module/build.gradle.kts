plugins {
    id("android-library-convention")
    id("kotlin-kapt-convention")
    id("android-compose-convention")
}

android {
    namespace = "com.gab.navigation_module"
}

dependencies {
    implementation(projects.themeApiModule)

    implementation(projects.modelModule)
    implementation(projects.modelMediaUsecases)

    implementation(projects.featureApiAllTracks)
    implementation(projects.featureApiPlaylists)
    implementation(projects.featureApiPlayer)
    implementation(libs.accompanist.permissions)
}