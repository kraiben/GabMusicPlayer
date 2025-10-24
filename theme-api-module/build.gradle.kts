plugins {
    id("android-library-convention")
    id("android-compose-convention")
}

android {
    namespace = "com.gab.theme_api_module"
}

dependencies {
    implementation(projects.modelModule)
}
