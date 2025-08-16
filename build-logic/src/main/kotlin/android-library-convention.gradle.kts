import com.android.build.gradle.LibraryExtension

plugins {
    id("org.jetbrains.kotlin.android")
    id("com.android.library")
    id("base-kotlin-convention")
    id("base-android-convention")
}

configure<LibraryExtension>  {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    kotlin {
        explicitApi()
    }
}