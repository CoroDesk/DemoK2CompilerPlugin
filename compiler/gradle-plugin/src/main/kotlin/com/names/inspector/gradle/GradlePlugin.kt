package com.names.inspector.gradle

import com.names.inspector.Constants
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilerPluginSupportPlugin
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetContainer
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

private const val EXTENSION_NAME = "NamesPluginConfig"

open class NamesPluginConfig {
    var enabled: Boolean = false
}

fun Project.configureNamesPlugin(kotlinExtension: KotlinSourceSetContainer, config: NamesPluginConfig.() -> Unit) {
    extensions.configure<NamesPluginConfig>(EXTENSION_NAME) {
        config(this)
            tasks.withType(KotlinCompilationTask::class.java).configureEach {
                this.compilerOptions {
                    languageVersion.set(KotlinVersion.KOTLIN_2_1)
                    apiVersion.set(KotlinVersion.KOTLIN_2_1)
                }
            }
    }
}

class GradlePlugin : KotlinCompilerPluginSupportPlugin {
    private var configExtension: NamesPluginConfig = NamesPluginConfig()

    override fun apply(target: Project) {
        target.extensions.create(EXTENSION_NAME, NamesPluginConfig::class.java)
        super.apply(target)
    }

    override fun applyToCompilation(kotlinCompilation: KotlinCompilation<*>): Provider<List<SubpluginOption>> {
        val registeredExtensions = kotlinCompilation.target.project.extensions
        configExtension = registeredExtensions.findByType(NamesPluginConfig::class.java) ?: NamesPluginConfig()

        return kotlinCompilation.target.project.provider {
            val options =
                mutableListOf(
                    SubpluginOption("enabled", configExtension.enabled.toString()),
                )
            options
        }
    }

    override fun getCompilerPluginId(): String = "NamesPlugin"

    override fun isApplicable(kotlinCompilation: KotlinCompilation<*>): Boolean {
        val registeredExtensions = kotlinCompilation.target.project.extensions
        val config = registeredExtensions.findByType(NamesPluginConfig::class.java) ?: NamesPluginConfig()
        return config.enabled
    }

    override fun getPluginArtifact(): SubpluginArtifact =
        SubpluginArtifact(
            groupId = SERIALIZATION_GROUP_NAME,
            artifactId = ARTIFACT_NAME,
            version = VERSION_NUMBER,
        )

    companion object {
        const val SERIALIZATION_GROUP_NAME = "com.names.inspector"
        const val ARTIFACT_NAME = "compiler-plugin"
        const val VERSION_NUMBER = Constants.PluginVersion
    }
}
