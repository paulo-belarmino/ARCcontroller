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
        maven( "https://repo.eclipse.org/content/repositories/paho-releases/")
        maven( "https://jitpack.io")
    }
}

rootProject.name = "ARC controller"
include(":app")
 