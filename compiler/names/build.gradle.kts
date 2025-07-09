 import org.gradle.kotlin.dsl.`maven-publish`
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    `maven-publish`
}

group = "com.names.inspector"
version = "0.0.1"


repositories {
    mavenCentral()
    mavenLocal()
    maven("https://maven.google.com")
    maven("https://plugins.gradle.org/m2/")
    google()
}


kotlin {
    compilerOptions {
        languageVersion = KotlinVersion.KOTLIN_2_1
        apiVersion = KotlinVersion.KOTLIN_2_1
    }
    jvmToolchain(17)

    applyDefaultHierarchyTemplate()
    jvm()
    androidTarget {
        publishLibraryVariants("release")
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {

        val commonMain by getting {
            dependencies {
             }
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("default") {
            pom {
                name.set("dogs")
                description.set("Dogs names validator library")
            }
        }
    }
}

android {
    namespace =  "com.names.inspector"

    compileSdk = 34
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}