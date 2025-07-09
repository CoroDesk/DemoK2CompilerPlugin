
plugins {
    kotlin("jvm")
    kotlin("kapt")
    id("java-gradle-plugin")
    `maven-publish`
    id("com.github.gmazzo.buildconfig") version "5.6.5"
    `kotlin-dsl`
}

group = "com.names.inspector"
version = "0.0.1"

repositories {
    mavenCentral()
}

buildConfig {
    className("Constants") // forces the class name. Defaults to 'BuildConfig'
    packageName("com.names.inspector") // forces the package. Defaults to '${project.group}'
    useKotlinOutput()
    buildConfigField("PluginVersion", "0.0.1")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin-api:2.1.21")
    implementation(kotlin("gradle-plugin", "2.1.21"))
}

gradlePlugin {
    plugins {
        create("names-gradle-plugin") {
            id = "com.names.inspector.plugin" // users will do `apply plugin: "com.names.inspector.plugin"`
            implementationClass = "com.names.inspector.gradle.GradlePlugin" // entry-point class
        }
    }
}

tasks.build {
    dependsOn(":compiler-plugin:publishToMavenLocal")
}
