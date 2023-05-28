/*
 * Copyright © 2020-2021, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.z2.sensible.kotlin

import com.google.auto.service.AutoService
import hu.simplexion.z2.sensible.kotlin.ir.generators.Generator
import hu.simplexion.z2.sensible.kotlin.ir.plugin.ComponentContainerContributor
import hu.simplexion.z2.sensible.kotlin.ir.plugin.ConfigurationKeys.ANNOTATION
import hu.simplexion.z2.sensible.kotlin.ir.plugin.ConfigurationKeys.DUMP
import hu.simplexion.z2.sensible.kotlin.ir.plugin.ConfigurationKeys.GENERATORS
import hu.simplexion.z2.sensible.kotlin.ir.plugin.ConfigurationKeys.PLUGIN_LOG_DIR
import hu.simplexion.z2.sensible.kotlin.ir.plugin.ConfigurationKeys.PRINT_DUMPS
import hu.simplexion.z2.sensible.kotlin.ir.plugin.DumpPoint
import hu.simplexion.z2.sensible.kotlin.ir.plugin.GenerationExtension
import hu.simplexion.z2.sensible.kotlin.ir.plugin.SensibleOptions
import hu.simplexion.z2.sensible.runtime.Plugin.SENSIBLE_ANNOTATION
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.JVMConfigurationKeys
import org.jetbrains.kotlin.extensions.StorageComponentContainerContributor

/**
 * Registers the extensions into the compiler.
 */
@OptIn(ExperimentalCompilerApi::class)
@AutoService(CompilerPluginRegistrar::class)
class SensibleCompilerPluginRegistrar(
    val generators: List<Generator> = emptyList(),
    val dumpPoints: List<DumpPoint> = emptyList(),
    val printDumps: Boolean = false,
    val pluginLogDir: String? = null
) : CompilerPluginRegistrar() {

    override val supportsK2 = true

    override fun ExtensionStorage.registerExtensions(configuration: CompilerConfiguration) {

        val options = SensibleOptions(
            annotations = configuration.get(ANNOTATION).let { if (! it.isNullOrEmpty()) it else listOf(SENSIBLE_ANNOTATION) },
            configuration.get(GENERATORS) ?: generators,
            configuration.get(DUMP) ?: dumpPoints,
            configuration.get(PRINT_DUMPS) ?: printDumps,
            configuration.get(PLUGIN_LOG_DIR) ?: pluginLogDir
        )
        registerComponents(options, configuration.getBoolean(JVMConfigurationKeys.IR))
    }

    fun ExtensionStorage.registerComponents(options: SensibleOptions, useIr: Boolean) {

        StorageComponentContainerContributor.registerExtension(
            ComponentContainerContributor(listOf(SENSIBLE_ANNOTATION), useIr)
        )

        IrGenerationExtension.registerExtension(
            GenerationExtension(options)
        )

    }

}
