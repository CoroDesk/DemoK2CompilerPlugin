package com.names.inspector

import com.google.auto.service.AutoService
import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.config.CompilerConfiguration

@AutoService(CommandLineProcessor::class) // don't forget!
class NamesCompilerCommandLineProcessor : CommandLineProcessor {
    override val pluginId: String = "NamesPlugin"

    override val pluginOptions: Collection<CliOption> =
        listOf(
            CliOption(
                optionName = "enabled",
                valueDescription = "<true|false>",
                description = "whether to enable the plugin or not",
            ),
        )

    override fun processOption(
        option: AbstractCliOption,
        value: String,
        configuration: CompilerConfiguration,
    ) = when (option.optionName) {
        "enabled" -> configuration.put(KEY_ENABLED, value.toBoolean())
        else -> configuration.put(KEY_ENABLED, true)
    }
}
