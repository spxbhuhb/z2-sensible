/*
 * Copyright © 2020-2021, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.z2.sensible.kotlin

import hu.simplexion.z2.sensible.kotlin.Plugin.SENSIBLE_ANNOTATION
import hu.simplexion.z2.sensible.kotlin.ir.generators.SensibleGenerator
import hu.simplexion.z2.sensible.kotlin.ir.plugin.SensibleConfigurationKeys.ANNOTATION
import hu.simplexion.z2.sensible.kotlin.ir.plugin.SensibleConfigurationKeys.DUMP
import hu.simplexion.z2.sensible.kotlin.ir.plugin.SensibleConfigurationKeys.GENERATORS
import hu.simplexion.z2.sensible.kotlin.ir.plugin.SensibleConfigurationKeys.PLUGIN_LOG_DIR
import hu.simplexion.z2.sensible.kotlin.ir.plugin.SensibleConfigurationKeys.PRINT_DUMPS
import hu.simplexion.z2.sensible.kotlin.ir.plugin.SensibleDumpPoint
import hu.simplexion.z2.sensible.kotlin.ir.plugin.SensibleGenerationExtension
import hu.simplexion.z2.sensible.kotlin.ir.plugin.SensibleOptions
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrarAdapter

/**
 * Registers the extensions into the compiler.
 */
@OptIn(ExperimentalCompilerApi::class)
class SensibleCompilerPluginRegistrar(
    val generators: List<SensibleGenerator> = emptyList(),
    val dumpPoints: List<SensibleDumpPoint> = emptyList(),
    val printDumps: Boolean = false,
    val pluginLogDir: String? = null
) : CompilerPluginRegistrar() {

    override val supportsK2 = true

    override fun ExtensionStorage.registerExtensions(configuration: CompilerConfiguration) {

        val options = SensibleOptions(
            configuration.get(ANNOTATION).let { if (! it.isNullOrEmpty()) it else listOf(SENSIBLE_ANNOTATION) },
            configuration.get(GENERATORS) ?: generators,
            configuration.get(DUMP) ?: dumpPoints,
            configuration.get(PRINT_DUMPS) ?: printDumps,
            configuration.get(PLUGIN_LOG_DIR) ?: pluginLogDir
        )

        FirExtensionRegistrarAdapter.registerExtension(SensiblePluginRegistrar())
        IrGenerationExtension.registerExtension(SensibleGenerationExtension(options))
    }

}
