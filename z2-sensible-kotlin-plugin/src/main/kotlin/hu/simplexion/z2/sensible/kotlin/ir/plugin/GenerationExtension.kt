/*
 * Copyright © 2020-2021, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.z2.sensible.kotlin.ir.plugin

import hu.simplexion.z2.sensible.kotlin.ir.PluginContext
import hu.simplexion.z2.sensible.kotlin.ir.SensibleTransform
import hu.simplexion.z2.sensible.runtime.Plugin.KOTLIN_COMPILER_PLUGIN_ID
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.util.IrMessageLogger
import org.jetbrains.kotlin.ir.util.dump

internal class GenerationExtension(
    val options: SensibleOptions
) : IrGenerationExtension {

    override fun generate(moduleFragment: IrModuleFragment, pluginContext: IrPluginContext) {

        PluginContext(
            pluginContext,
            options,
            pluginContext.createDiagnosticReporter(KOTLIN_COMPILER_PLUGIN_ID),
            moduleFragment
        ).apply {

            // --------  preparations  --------

            pluginLogDir?.let {
                diagnosticReporter.report(
                    IrMessageLogger.Severity.WARNING,
                    "sensible.pluginLogDir is set to: $it",
                    IrMessageLogger.Location(moduleFragment.name.asString(), 1, 1)
                )
            }

            DumpPoint.Before.dump(this) {
                output("DUMP BEFORE", moduleFragment.dump())
            }

            // --------  transformation  --------

            moduleFragment.accept(SensibleTransform(this), null)

            // --------  finishing up  --------

            DumpPoint.After.dump(this) {
                output("DUMP AFTER", moduleFragment.dump())
            }

        }
    }
}

