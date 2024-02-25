plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.firebase.crashlytics")
}

android {
    signingConfigs {
        create("release") {
            storeFile = file("d:\\Android\\MTGCollection\\keystore.jks")
            keyAlias = "key0"
            storePassword = "fake_password"
            keyPassword = "fake_password"
        }
    }
    namespace = "com.andreikslpv.mtgcollection"
    compileSdk = rootProject.extra["targetAndroidSdk"] as Int

    defaultConfig {
        applicationId = "com.andreikslpv.mtgcollection"
        minSdk = rootProject.extra["minAndroidSdk"] as Int
        targetSdk = rootProject.extra["targetAndroidSdk"] as Int
        versionCode = 6
        versionName = "1.0.5"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = rootProject.extra["javaVersion"] as JavaVersion
        targetCompatibility = rootProject.extra["javaVersion"] as JavaVersion
    }
    kotlinOptions {
        jvmTarget = rootProject.extra["kotlinVersion"] as String
    }
}

kapt {
    correctErrorTypes = true
}

hilt {
    enableAggregatingTask = true
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.paging:paging-runtime-ktx:3.2.1")

    implementation("com.google.dagger:hilt-android:2.50")
    kapt("com.google.dagger:hilt-compiler:2.50")
    implementation("com.google.android.gms:play-services-auth:21.0.0")
    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.2"))
    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-messaging")

    implementation(project(":wiring"))
    implementation(project(":navigation"))
    implementation(project(":core:presentation"))
    implementation(project(":core:common-impl"))
    implementation(project(":core:domain"))

    implementation(project(":features:auth:presentation-auth"))
    implementation(project(":features:auth:domain-auth"))
    implementation(project(":features:auth:data-auth"))

    implementation(project(":features:cards:presentation-cards"))
    implementation(project(":features:cards:domain-cards"))
    implementation(project(":features:cards:data-cards"))

    implementation(project(":features:settings:presentation-settings"))
    implementation(project(":features:settings:domain-settings"))
    implementation(project(":features:settings:data-settings"))

    implementation(project(":features:sets:presentation-sets"))
    implementation(project(":features:sets:domain-sets"))
    implementation(project(":features:sets:data-sets"))

    implementation(project(":features:users:domain-users"))
    implementation(project(":features:users:data-users"))

    testImplementation("junit:junit:4.13.2")
}
