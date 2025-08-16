plugins {
   `kotlin-dsl`
}

repositories {
    google {
        content {
            includeGroupByRegex("com\\.android.*")
            includeGroupByRegex("com\\.google.*")
            includeGroupByRegex("androidx.*")
        }
    }
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
   implementation(libs.gradleplugin.android)
   implementation(libs.gradleplugin.kotlin)
   implementation(libs.gradleplugin.composeCompiler)
   implementation(libs.gradleplugin.compose)
    implementation(libs.org.jetbrains.kotlin.plugin.serialization.gradle.plugin)
   implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}