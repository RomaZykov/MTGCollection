plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.andreikslpv.data_cards"
    compileSdk = rootProject.extra["targetAndroidSdk"] as Int

    defaultConfig {
        minSdk = rootProject.extra["minAndroidSdk"] as Int

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = rootProject.extra["javaVersion"] as JavaVersion
        targetCompatibility = rootProject.extra["javaVersion"] as JavaVersion
    }
    kotlinOptions {
        jvmTarget = rootProject.extra["kotlinVersion"] as String
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.paging.runtime.ktx)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)
    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)

    implementation(project(":core:data"))
    implementation(project(":features:cards:domain-cards"))
    implementation(project(":features:cards:datasource-room-cards"))

    testImplementation(libs.junit)
}