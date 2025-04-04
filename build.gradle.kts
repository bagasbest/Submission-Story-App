// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("com.google.devtools.ksp") version "2.0.0-1.0.24" apply false //dengan asumsi versi Kotlin 2.0.0
    alias(libs.plugins.secrets.gradle.plugin) apply false
    id ("org.jetbrains.kotlinx.kover") version "0.5.0"
    id("com.google.gms.google-services") version "4.4.2" apply false
    alias(libs.plugins.hiltAndroid) apply false
}