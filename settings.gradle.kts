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
// settings.gradle.kts

dependencyResolutionManagement {
    repositories {
        // Other repositories
        maven { url = uri("https://jitpack.io") } // Add this line for MPAndroidChart
    }
}

rootProject.name = "AndroidStudioPlanty"
include(":app")
 