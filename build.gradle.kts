// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {

    val targetAndroidSdk by extra(34)
    val minAndroidSdk by extra(24)

    val javaVersion by extra(JavaVersion.VERSION_17)
    val kotlinVersion by extra("17")

    dependencies {
        classpath("com.google.gms:google-services:4.4.1")
        classpath("com.android.tools.build:gradle:8.2.2")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.9")
    }
}

plugins {
    id("com.google.dagger.hilt.android") version "2.50" apply false
    id("com.android.application") version "8.2.2" apply false
    id("com.android.library") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    id("org.jetbrains.kotlin.jvm") version "1.9.22" apply false
}