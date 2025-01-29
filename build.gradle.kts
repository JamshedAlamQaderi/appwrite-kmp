//buildscript {
//    dependencies {
//        classpath("org.jetbrains.kotlinx:atomicfu-gradle-plugin:0.27.0")
//    }
//}

plugins {
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.atomicfu) apply false
}

group = "com.jamshedalamqaderi"
version = "1.0-SNAPSHOT"


subprojects {
    apply(plugin = rootProject.libs.plugins.ktlint.get().pluginId)

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        filter {
            exclude {
                it.file.path.contains(
                    java.nio.file.Paths.get("${project.layout.buildDirectory.get()}/generated").toString(),
                )
            }
        }
    }
}
