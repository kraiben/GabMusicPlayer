enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
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
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "gabs_music_player"

include(":app")
include(":feature-playlists")
include(":injection-module")
include(":core-music-loading")
include(":model-module")
include(":core-settings")
include(":core-media")
include(":music-entities-module")
include(":feature-all-tracks")
include(":model-media-usecases")
include(":testapp")
include(":feature-options-menus")
