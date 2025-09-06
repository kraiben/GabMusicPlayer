import com.android.build.gradle.BaseExtension

configure<BaseExtension> {
    setCompileSdkVersion(rootProject.extra["compileSdkVersion"] as Int)
    defaultConfig {

        minSdk = rootProject.extra["minSdkVersion"] as Int
        setTargetSdkVersion(rootProject.extra["compileSdkVersion"] as Int)
        versionCode = 1
        versionName = "1.0"
    }
    compileOptions {
        targetCompatibility = JavaVersion.VERSION_17
        sourceCompatibility = JavaVersion.VERSION_17
    }
}
dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}