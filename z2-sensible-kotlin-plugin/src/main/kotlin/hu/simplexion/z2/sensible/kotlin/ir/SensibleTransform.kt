/*
 * Copyright © 2020-2021, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.z2.sensible.kotlin.ir

import hu.simplexion.z2.sensible.kotlin.ir.diagnostics.ErrorsSensible.ERROR_MISSING_PRIMARY_CONSTRUCTOR
import org.jetbrains.kotlin.ir.IrElement
import org.jetbrains.kotlin.ir.builders.declarations.addConstructor
import org.jetbrains.kotlin.ir.declarations.IrClass
import org.jetbrains.kotlin.ir.declarations.IrConstructor
import org.jetbrains.kotlin.ir.expressions.impl.IrDelegatingConstructorCallImpl
import org.jetbrains.kotlin.ir.util.SYNTHETIC_OFFSET
import org.jetbrains.kotlin.ir.util.defaultType
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
import org.jetbrains.kotlin.psi.KtModifierListOwner

class SensibleTransform(
    private val context: PluginContext
) : IrElementVisitorVoid, SensibleAnnotationBasedExtension {

    val irBuiltIns = context.irContext.irBuiltIns

    val irFactory = context.irContext.irFactory

    override fun getAnnotationFqNames(modifierListOwner: KtModifierListOwner?): List<String> =
        context.annotations

    override fun visitElement(element: IrElement) {
        element.acceptChildren(this, null)
    }

    override fun visitClass(declaration: IrClass) {

        if (declaration.isAnnotatedWithSensible()) {
            addSensibleConstructorIfNeeded(declaration)
        }

        visitElement(declaration)
    }

    fun addSensibleConstructorIfNeeded(irClass: IrClass) {

        var primary: IrConstructor? = null

        for (declaration in irClass.declarations) {
            if (declaration !is IrConstructor) continue

            // don't touch it if it already has an empty constructor
            if (declaration.valueParameters.isEmpty()) return

            if (declaration.isPrimary) {
                primary = declaration
                break
            }
        }

        if (primary == null) {
            ERROR_MISSING_PRIMARY_CONSTRUCTOR.report(context, irClass)
            return
        }

        addSensibleConstructor(irClass, primary)

        return
    }

    private fun addSensibleConstructor(irClass: IrClass, primary: IrConstructor) {

        irClass.addConstructor {

            returnType = irClass.defaultType

        }.apply {

            body = irFactory.createBlockBody(SYNTHETIC_OFFSET, SYNTHETIC_OFFSET).apply {
                statements += IrDelegatingConstructorCallImpl.fromSymbolOwner(
                    SYNTHETIC_OFFSET,
                    SYNTHETIC_OFFSET,
                    irClass.defaultType,
                    primary.symbol,
                    typeArgumentsCount = 0,
                    valueArgumentsCount = primary.valueParameters.size
                ).apply {

                    for (valueParameter in primary.valueParameters) {

                        val generator = context.generators[valueParameter.type]
                            ?: throw RuntimeException("unknown type: ${valueParameter.type}")

                        putValueArgument(valueParameter.index, generator.expression())

                    }

                }
            }
        }
    }
}
