plugins {
    id("com.android.dynamic-feature")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

apply("../shared_dependencies.gradle")

android {
    namespace = "com.example.githubusersearch.favorites"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":app"))
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.4")
}

kapt {
    correctErrorTypes = true
}