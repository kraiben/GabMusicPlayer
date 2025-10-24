plugins {
    id("android-library-convention")
    id("kotlin-kapt-convention")
    id("android-compose-convention")
}

android {
    namespace = "com.gab.theme_module"
}

dependencies {
    implementation(projects.modelModule)
    implementation(projects.themeApiModule)
}