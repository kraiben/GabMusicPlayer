import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
kotlinJvmModule {
    explicitApi()
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}
internal fun Project.java(configure: Action<JavaPluginExtension>): Unit =
    (this as ExtensionAware).extensions.configure("java", configure)


internal fun Project.kotlinJvmModule(configure: Action<org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension>): Unit =
    (this as ExtensionAware).extensions.configure("kotlin", configure)