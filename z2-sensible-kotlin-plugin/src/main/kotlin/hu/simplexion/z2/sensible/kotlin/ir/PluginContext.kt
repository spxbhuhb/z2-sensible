/*
 * Copyright © 2020-2021, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.z2.sensible.kotlin.ir

import hu.simplexion.z2.sensible.kotlin.ir.generators.Generator
import hu.simplexion.z2.sensible.kotlin.ir.generators.IntGenerator
import hu.simplexion.z2.sensible.kotlin.ir.plugin.SensibleOptions
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.declarations.IrDeclaration
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.types.IrType
import org.jetbrains.kotlin.ir.util.IrMessageLogger
import org.jetbrains.kotlin.ir.util.fileEntry
import java.nio.file.Path
import java.nio.file.Paths
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.io.path.appendText
import kotlin.io.path.createDirectories
import kotlin.io.path.createFile

class PluginContext(
    val irContext: IrPluginContext,
    options: SensibleOptions,
    val diagnosticReporter: IrMessageLogger,
    val moduleFragment: IrModuleFragment
) {
    val annotations = options.annotations
    val dumpPoints = options.dumpPoints
    val printDumps = options.printDumps

    val pluginLogDir: Path? = options.pluginLogDir?.let { Paths.get(options.pluginLogDir).also { it.createDirectories() } }
    val pluginLogTimestamp: String = DateTimeFormatter.ofPattern("yyyyMMdd'-'HHmmss").format(LocalDateTime.now())
    val pluginLogFile: Path? = pluginLogDir?.resolve("sensible-log-$pluginLogTimestamp.txt").also { it?.createFile() }

    val generators = mutableMapOf<IrType, Generator>(
        irContext.irBuiltIns.intType to IntGenerator(this)
    )

    fun output(title: String, content: String, declaration: IrDeclaration? = null) {

        val longTitle = "\n\n====  $title  ================================================================\n"

        pluginLogFile?.appendText("$longTitle\n\n$content")

        if (printDumps) {
            println(longTitle)
            println(content)
        } else {
            diagnosticReporter.report(
                IrMessageLogger.Severity.INFO,
                title,
                IrMessageLogger.Location(
                    declaration?.fileEntry?.name ?: moduleFragment.name.asString(),
                    declaration?.fileEntry?.getLineNumber(declaration.startOffset) ?: 1,
                    declaration?.fileEntry?.getColumnNumber(declaration.startOffset) ?: 1
                )
            )
        }
    }
}

