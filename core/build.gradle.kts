plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

apply("../shared_dependencies.gradle")

android {
    namespace = "com.example.githubusersearch.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    buildTypes {
        release {
            buildConfigField("String", "Base_API", "\"https://api.github.com/\"")
            buildConfigField("String", "API_Key", "\"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\"")
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
        debug {
            buildConfigField("String", "Base_API", "\"https://api.github.com/\"")
            buildConfigField("String", "API_Key", "\"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\"")
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

}

dependencies {
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    implementation("androidx.room:room-runtime:2.5.0")
    implementation("androidx.room:room-ktx:2.5.0")
    ksp("androidx.room:room-compiler:2.5.0")

    implementation("net.zetetic:android-database-sqlcipher:4.4.0")
    implementation("androidx.sqlite:sqlite-ktx:2.4.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    api("androidx.lifecycle:lifecycle-livedata-ktx:2.8.4")
}