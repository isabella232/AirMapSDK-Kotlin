plugins {
    kotlin("jvm")
    kotlin("kapt")
}

ktlint {
    debug.set(true)
}

// TODO: Add R8/ProGuard rules for all included libraries (e.g. retrofit, okhttp, moshi)
dependencies {
    implementation(kotlin("stdlib"))

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.7.2")
    implementation("com.squareup.retrofit2:converter-moshi:2.7.2")

    // Moshi
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.9.2")
    implementation("com.squareup.moshi:moshi:1.9.2")
    implementation("com.squareup.moshi:moshi-adapters:1.9.2")
    implementation("com.serjltt.moshi:moshi-lazy-adapters:2.2")
    api("com.aungkyawpaing.geoshi:geoshi-adapter:0.0.2")
}

// TODO: https://kotlinlang.org/docs/tutorials/android-plugin.html specifically, "Custom Parcelers".
//  Create an "android" specific extension module and create custom parcelers for the data models
