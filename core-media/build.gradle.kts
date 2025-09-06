plugins {
    id("android-library-convention")
    id("kotlin-kapt-convention")
}

android {
    namespace = "com.gab.core_media"
}

dependencies {
    implementation(projects.musicEntitiesModule)
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.exoplayer.dash)
    implementation(libs.androidx.media3.ui)
    implementation(libs.androidx.media3.session)
    implementation(libs.androidx.media)
}