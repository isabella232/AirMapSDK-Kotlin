plugins {
    kotlin("jvm")
    kotlin("kapt")
}

ktlint {
    debug.set(true)
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.5")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.8.1")
    implementation("com.squareup.retrofit2:converter-moshi:2.8.1")

    // Moshi
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.9.2")
    implementation("com.squareup.moshi:moshi:1.9.2")
    implementation("com.squareup.moshi:moshi-adapters:1.9.2")
    implementation("com.serjltt.moshi:moshi-lazy-adapters:2.2")
    implementation("dev.zacsweers.moshisealed:moshi-sealed-annotations:0.1.0")
    kapt("dev.zacsweers.moshisealed:moshi-sealed-codegen:0.1.0")
    api("com.aungkyawpaing.geoshi:geoshi-adapter:0.0.2")
}
