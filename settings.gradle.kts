rootProject.name = providers.gradleProperty("mod.id").get()

pluginManagement {
    repositories {
        // RetroFuturaGradle
        maven {
            name = "GTNH Maven"
            url = uri("http://jenkins.usrv.eu:8081/nexus/content/groups/public/")
            isAllowInsecureProtocol = true
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
