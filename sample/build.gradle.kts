import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.atomicfu)
}

//apply(plugin = "kotlinx-atomicfu")

kotlin {
    androidTarget()
    jvm()

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }
    js {
        browser()
        binaries.executable()
    }

    sourceSets{
        commonMain.dependencies {
            implementation(project(":library"))
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.io)
        }
    }
}

android{
    namespace = "com.jamshedalamqaderi.appwrite.kmp.sample"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
}