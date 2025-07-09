
includeBuild("compiler") {
    dependencySubstitution {
        substitute(module("com.names.inspector:gradle-plugin")).using(project(":gradle-plugin"))
        substitute(module("com.names.inspector:names")).using(project(":names"))
    }
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositories {
        mavenCentral()
        mavenLocal()
    }
}
rootProject.name = "NamesCompilerPlugin"

include(":app")
