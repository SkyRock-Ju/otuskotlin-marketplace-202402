plugins {
    kotlin("jvm")
}

group = "com.otus.otuskotlin.social"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test-junit"))
}

kotlin {
    jvmToolchain(17)
}