/*
 * Copyright © 2020-2021, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.z2.sensible.kotlin.ir.diagnostics

import hu.simplexion.z2.sensible.kotlin.ir.PluginContext
import org.jetbrains.kotlin.diagnostics.Errors
import org.jetbrains.kotlin.ir.IrFileEntry
import org.jetbrains.kotlin.ir.declarations.IrDeclaration
import org.jetbrains.kotlin.ir.util.IrMessageLogger
import org.jetbrains.kotlin.ir.util.file

object ErrorsSensible {
    // These errors are used by the IR transformation

    // IMPORTANT: DO NOT ADD AUTOMATIC ID GENERATION! error ids should not change over time
    val ERROR_MISSING_PRIMARY_CONSTRUCTOR = SensibleIrError(1, "Missing primary constructor.")

    class SensibleIrError(
        val id: Int,
        val message: String,
    ) {
        fun toMessage(): String {
            return "${id.toString().padStart(4, '0')}  $message"
        }

        fun report(context: PluginContext, declaration: IrDeclaration, additionalInfo: String = "") {
            report(context, declaration.file.fileEntry, declaration.startOffset, additionalInfo)
        }

        fun report(context: PluginContext, fileEntry: IrFileEntry, offset: Int, additionalInfo: String = "") {
            context.diagnosticReporter.report(
                IrMessageLogger.Severity.ERROR,
                toMessage() + " " + additionalInfo,
                IrMessageLogger.Location(
                    fileEntry.name,
                    fileEntry.getLineNumber(offset) + 1,
                    fileEntry.getColumnNumber(offset) + 1
                )
            )
        }
    }

    init {
        Errors.Initializer.initializeFactoryNamesAndDefaultErrorMessages(ErrorsSensible::class.java, DefaultErrorMessagesSensible)
    }
}