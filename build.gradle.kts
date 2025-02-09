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
    alias(libs.plugins.vanniktech.mavenPublish) apply false
}

subprojects {
    apply(plugin = rootProject.libs.plugins.ktlint.get().pluginId)

    group = "com.jamshedalamqaderi.kmp"
    version = project.property("version") as String

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
