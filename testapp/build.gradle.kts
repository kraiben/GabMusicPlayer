plugins {
    id("android-application-convention")
    id("kotlin-kapt-convention")
}

android {
    namespace = "com.gab.testapp"

    defaultConfig {
        applicationId = "com.gab.testapp"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}
dependencies {
    implementation(projects.coreMedia)
    implementation(projects.musicEntitiesModule)
}