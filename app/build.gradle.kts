import com.names.inspector.gradle.configureNamesPlugin

plugins {
    kotlin("multiplatform")
    id("com.names.inspector.plugin") version "0.0.1"
}

kotlin {
    jvmToolchain(17)
}

configureNamesPlugin(kotlin) {
    enabled = true
}

kotlin {
    jvm()
    iosArm64()
    iosX64()
    sourceSets {
        commonMain {
            dependencies {
                implementation("com.names.inspector:names:0.0.1")
            }
        }
    }
}
