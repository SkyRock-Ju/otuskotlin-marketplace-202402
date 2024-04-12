rootProject.name = "otuskotlin-marketplace-202402"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        kotlin("jvm") version kotlinVersion
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

includeBuild("lessons")
includeBuild("ok-chat-be")
includeBuild("build-plugin")