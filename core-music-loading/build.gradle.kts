plugins {
    id("android-library-convention")
//    id("kotlin-serialization-convention")
    id("kotlin-kapt-convention")
}
android {
    namespace = "com.gab.core_music_loading"
}
dependencies {
    implementation(projects.musicEntitiesModule)
    implementation(libs.androidx.room)
    kapt(libs.androidx.room.copiler)
    implementation(libs.androidx.room.ktx)
}