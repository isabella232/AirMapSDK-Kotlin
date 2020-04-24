// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1"
}

buildscript {
    repositories {
        google()
        jcenter()
        maven("http://dl.bintray.com/kotlin/kotlin-dev") // TODO: Remove once no longer on EAP
    }

    dependencies {
        classpath("com.android.tools.build:gradle:3.6.3")
        // TODO: Update version (using dev instead of eap beacuse of https://youtrack.jetbrains.com/issue/KT-27586)
        classpath(kotlin("gradle-plugin", version = "1.4.0-dev-7659"))
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    repositories {
        google()
        jcenter()
        maven("http://dl.bintray.com/kotlin/kotlin-dev") // TODO: Remove once no longer on EAP
    }
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}
