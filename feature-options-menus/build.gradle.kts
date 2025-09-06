plugins {
    id("android-library-convention")
    id("kotlin-kapt-convention")
    id("android-compose-convention")
}

android {
    namespace = "com.gab.feature_options_menus"
}

dependencies {
    implementation(projects.musicEntitiesModule)
    implementation(projects.modelMediaUsecases)
    implementation(projects.modelModule)
}