 import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    kotlin("jvm")
    kotlin("kapt")
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
}

dependencies {
    implementation("com.google.auto.service:auto-service-annotations:1.1.1")
    implementation(project(":names"))
    kapt("com.google.auto.service:auto-service:1.1.1")
    implementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:2.1.21")
}

publishing {
    publications {
        create<MavenPublication>("default") {
            from(components["java"])

            pom {
                name.set("compiler-plugin")
                description.set("Names Validation Compiler Plugin")
            }
        }
    }
}

tasks.withType<KotlinCompilationTask<*>>().configureEach {
    compilerOptions.freeCompilerArgs.add("-opt-in=org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi")
}
