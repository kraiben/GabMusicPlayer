import org.jetbrains.kotlin.gradle.dsl.JvmTarget

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

internal fun Project.kotlin(configure: Action<org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension>): Unit =
    (this as ExtensionAware).extensions.configure("kotlin", configure)
