plugins {
    id("android-library-convention")
    id("kotlin-kapt-convention")
}

android {
    namespace = "com.gab.model_module"
}

dependencies {
    implementation(projects.coreMusicLoading)
    implementation(projects.coreSettings)
}