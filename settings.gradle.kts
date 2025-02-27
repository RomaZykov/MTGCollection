pluginManagement {
    repositories {
        google()
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
rootProject.name = "MTGCollection"
include(":app")
include(":features")
include(":navigation")
include(":wiring")
include(":core")
include(":core:theme")
include(":core:presentation")
include(":core:common")
include(":core:common-impl")
include(":features:auth:presentation-auth")
include(":features:auth:data-auth")
include(":features:auth:domain-auth")
include(":core:domain")
include(":features:settings:data-settings")
include(":features:settings:presentation-settings")
include(":features:settings:domain-settings")
include(":features:cards:data-cards")
include(":features:cards:presentation-cards")
include(":features:cards:domain-cards")
include(":core:data")
include(":features:users:data-users")
include(":features:users:domain-users")
include(":features:sets:presentation-sets")
include(":features:sets:data-sets")
include(":features:sets:domain-sets")
include(":features:cards:datasource-room-cards")
include(":features:sets:datasource-room-sets")
