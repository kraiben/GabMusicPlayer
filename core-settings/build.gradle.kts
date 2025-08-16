plugins {
    id("android-library-convention")
    id("kotlin-kapt-convention")
}
android {
    namespace = "com.gab.core_settings"
}
dependencies {
    implementation(libs.datastore.preferences)
}