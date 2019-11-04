import org.jetbrains.kotlin.config.KotlinCompilerVersion
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(29)
    defaultConfig {
        minSdkVersion(23)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }


    (kotlinOptions as KotlinJvmOptions).apply {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk7", KotlinCompilerVersion.VERSION))

    implementation("androidx.core:core-ktx:1.1.0")
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.security:security-crypto:1.0.0-alpha02")

    implementation("com.squareup.retrofit2:retrofit:2.6.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.6.0")
    implementation("com.squareup.moshi:moshi:1.8.0")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.8.0")
    implementation("com.serjltt.moshi:moshi-lazy-adapters:2.2")
    debugImplementation("com.readystatesoftware.chuck:library:1.1.0") // TODO: Remove?
    releaseImplementation("com.readystatesoftware.chuck:library-no-op:1.1.0") // TODO: Remove?

    api("com.jakewharton.timber:timber:4.7.1")
    api("com.jakewharton.threetenabp:threetenabp:1.2.1") // java.time.*

    api("com.mapbox.mapboxsdk:mapbox-android-sdk:8.2.0")
}
