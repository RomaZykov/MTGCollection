// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {

    val compileAndroidSdk by extra(35)
    val targetAndroidSdk by extra(34)
    val minAndroidSdk by extra(24)

    val javaVersion by extra(JavaVersion.VERSION_17)
    val kotlinVersion by extra("17")

    dependencies {
        classpath(libs.google.services)
        classpath(libs.gradle)
        classpath(libs.firebase.crashlytics.gradle)
    }
}

plugins {
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.devtools.ksp) apply false
}