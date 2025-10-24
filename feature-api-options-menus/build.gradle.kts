plugins {
    id("android-library-convention")
    id("android-compose-convention")
}

android {
    namespace = "com.gab.feature_api_options_menus"}

dependencies {

    implementation(projects.musicEntitiesModule)
}