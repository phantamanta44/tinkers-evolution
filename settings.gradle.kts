rootProject.name = providers.gradleProperty("mod.id").get()

pluginManagement {
    repositories {
        mavenLocal()
        // RetroFuturaGradle
        maven {
            name = "GTNH Maven"
            url = uri("https://nexus.gtnewhorizons.com/repository/public/")
            mavenContent {
                includeGroupByRegex("com\\.gtnewhorizons\\..+")
                includeGroup("com.gtnewhorizons")
            }
        }
        gradlePluginPortal()
        mavenCentral()
    }
}

plugins {
    // If in a Nix shell, just use toolchains provided by Nix
    if (System.getenv("IN_NIX_SHELL") == null) {
        // Automatic toolchain provisioning
        id("org.gradle.toolchains.foojay-resolver-convention") version "0.4.0"
    }
}
