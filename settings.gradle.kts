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
include(":music-entities-module")
include(":core-music-loading")
include(":core-media")
include(":core-settings")
include(":model-module")
include(":model-media-usecases")
include(":feature-api-options-menus")
include(":feature-options-menus")
include(":feature-playlists")
include(":feature-all-tracks")
include(":testapp")
include(":navigation-module")
include(":theme-module")
include(":feature-api-all-tracks")
include(":feature-api-playlists")
include(":theme-api-module")
include(":feature-player")
include(":feature-api-player")
