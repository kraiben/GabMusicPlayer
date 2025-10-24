plugins {
    id("android-library-convention")
    id("android-compose-convention")
    id("kotlin-kapt-convention")
}

android {
    namespace = "com.gab.feature_player"
}

dependencies {
    implementation(projects.musicEntitiesModule)
    implementation(projects.modelMediaUsecases)
    implementation(projects.featureApiPlayer)
}