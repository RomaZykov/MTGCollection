plugins {
    id("java-library")
    alias(libs.plugins.kotlin.jvm)
}

java {
    sourceCompatibility = rootProject.extra["javaVersion"] as JavaVersion
    targetCompatibility = rootProject.extra["javaVersion"] as JavaVersion
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)

    implementation(project(":core:common"))
    implementation(project(":core:domain"))
}