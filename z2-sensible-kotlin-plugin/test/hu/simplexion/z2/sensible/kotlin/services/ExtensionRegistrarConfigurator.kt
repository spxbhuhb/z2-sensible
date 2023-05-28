package hu.simplexion.z2.sensible.kotlin.services

import hu.simplexion.z2.sensible.kotlin.Plugin
import hu.simplexion.z2.sensible.kotlin.SensiblePluginRegistrar
import hu.simplexion.z2.sensible.kotlin.ir.plugin.SensibleDumpPoint
import hu.simplexion.z2.sensible.kotlin.ir.plugin.SensibleGenerationExtension
import hu.simplexion.z2.sensible.kotlin.ir.plugin.SensibleOptions
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrarAdapter
import org.jetbrains.kotlin.test.model.TestModule
import org.jetbrains.kotlin.test.services.EnvironmentConfigurator
import org.jetbrains.kotlin.test.services.TestServices

class ExtensionRegistrarConfigurator(testServices: TestServices) : EnvironmentConfigurator(testServices) {
    override fun CompilerPluginRegistrar.ExtensionStorage.registerCompilerExtensions(
        module: TestModule,
        configuration: CompilerConfiguration
    ) {
        FirExtensionRegistrarAdapter.registerExtension(SensiblePluginRegistrar())

        IrGenerationExtension.registerExtension(
            SensibleGenerationExtension(
                SensibleOptions(
                    listOf(Plugin.SENSIBLE_ANNOTATION),
                    emptyList(),
                    SensibleDumpPoint.values().toList(),
                    printDumps = true,
                    null
                )
            )
        )
    }
}