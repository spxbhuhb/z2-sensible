/*
 * Copyright © 2020-2021, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.z2.sensible.kotlin.ir.plugin

import com.google.auto.service.AutoService
import hu.simplexion.z2.sensible.runtime.Plugin.KOTLIN_COMPILER_PLUGIN_ID
import hu.simplexion.z2.sensible.runtime.Plugin.OPTION_NAME_ANNOTATION
import hu.simplexion.z2.sensible.runtime.Plugin.OPTION_NAME_DUMP_POINT
import hu.simplexion.z2.sensible.runtime.Plugin.OPTION_NAME_PLUGIN_LOG_DIR
import hu.simplexion.z2.sensible.runtime.Plugin.OPTION_NAME_PRINT_DUMPS
import org.jetbrains.kotlin.compiler.plugin.*
import org.jetbrains.kotlin.config.CompilerConfiguration

@OptIn(ExperimentalCompilerApi::class)
@AutoService(CommandLineProcessor::class)
class CommandLineProcessor : org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor {
    companion object {

        val OPTION_ANNOTATION = CliOption(
            OPTION_NAME_ANNOTATION, "<fqname>", "Annotation qualified names",
            required = false, allowMultipleOccurrences = true
        )
        val OPTION_DUMP = CliOption(
            OPTION_NAME_DUMP_POINT, "string", "Dump data at specified points of plugin run (${DumpPoint.optionValues().joinToString { ", " }}).",
            required = false, allowMultipleOccurrences = true
        )
        val OPTION_PRINT_DUMPS = CliOption(
            OPTION_NAME_PRINT_DUMPS, "boolean", "Use println for output instead of the compiler logging framework",
            required = false, allowMultipleOccurrences = false
        )
        val OPTION_PLUGIN_LOG_DIR = CliOption(
            OPTION_NAME_PLUGIN_LOG_DIR, "string", "Save plugin output into a file in this directory.",
            required = false, allowMultipleOccurrences = false
        )
    }

    override val pluginId = KOTLIN_COMPILER_PLUGIN_ID

    override val pluginOptions = listOf(
        OPTION_ANNOTATION,
        OPTION_DUMP,
        OPTION_PRINT_DUMPS,
        OPTION_PLUGIN_LOG_DIR
    )

    override fun processOption(option: AbstractCliOption, value: String, configuration: CompilerConfiguration) {
        when (option) {
            OPTION_ANNOTATION -> configuration.appendList(ConfigurationKeys.ANNOTATION, value)
            OPTION_DUMP -> configuration.appendList(ConfigurationKeys.DUMP, value.toDumpPoint())
            OPTION_PRINT_DUMPS -> configuration.put(ConfigurationKeys.PRINT_DUMPS, value.toBooleanStrictOrNull() ?: false)
            OPTION_PLUGIN_LOG_DIR -> configuration.put(ConfigurationKeys.PLUGIN_LOG_DIR, value)
            else -> throw CliOptionProcessingException("Unknown option: ${option.optionName}")
        }
    }

    private fun String.toDumpPoint(): DumpPoint =
        DumpPoint.fromOption(this) ?: throw CliOptionProcessingException("Unknown dump point: $this")

}