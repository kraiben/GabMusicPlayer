plugins {
    id("android-library-convention")
    id("kotlin-serialization-convention")
}
android {
    namespace = "com.gab.music_entities_module"
}
dependencies {
    implementation(libs.androidx.compose.runtime)
}