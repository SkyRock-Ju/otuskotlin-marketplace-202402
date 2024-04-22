rootProject.name = "ok-social-be"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

pluginManagement {
    val kotlinVersion: String by settings

    plugins {
        id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
        kotlin("jvm") version kotlinVersion
    }
}


