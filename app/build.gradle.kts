plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
    id("kotlin-kapt")
    alias(libs.plugins.hilt.android)
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
        versionCode = 7
        versionName = "1.1.0"

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
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.paging.runtime.ktx)

    implementation(libs.google.play.services.auth)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics.core)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.messaging)

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

    testImplementation(libs.junit)
}
