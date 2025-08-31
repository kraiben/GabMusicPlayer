plugins {
    id("android-library-convention")
    id("kotlin-kapt-convention")
}
android {
    namespace = "com.gab.model_media_usecases"
}

dependencies {
    implementation(projects.musicEntitiesModule)
    implementation(projects.coreMedia)
}