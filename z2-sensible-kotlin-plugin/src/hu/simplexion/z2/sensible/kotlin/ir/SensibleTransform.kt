/*
 * Copyright © 2020-2021, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.z2.sensible.kotlin.ir

import hu.simplexion.z2.sensible.kotlin.fir.SensibleConstructorGenerator
import org.jetbrains.kotlin.ir.IrElement
import org.jetbrains.kotlin.ir.declarations.IrClass
import org.jetbrains.kotlin.ir.declarations.IrConstructor
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin
import org.jetbrains.kotlin.ir.expressions.impl.IrDelegatingConstructorCallImpl
import org.jetbrains.kotlin.ir.util.SYNTHETIC_OFFSET
import org.jetbrains.kotlin.ir.util.defaultType
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
import org.jetbrains.kotlin.psi.KtModifierListOwner

class SensibleTransform(
    private val context: SensiblePluginContext
) : IrElementVisitorVoid, SensibleAnnotationBasedExtension {

    val irFactory = context.irContext.irFactory

    override fun getAnnotationFqNames(modifierListOwner: KtModifierListOwner?): List<String> =
        context.annotations

    override fun visitElement(element: IrElement) {
        element.acceptChildren(this, null)
    }

    override fun visitClass(declaration: IrClass) {

        super.visitClass(declaration)

        if (declaration.isAnnotatedWithSensible()) {
            addSensibleConstructorIfNeeded(declaration)
        }
    }

    fun addSensibleConstructorIfNeeded(irClass: IrClass) {

        var primary: IrConstructor? = null
        var sensible: IrConstructor? = null

        for (declaration in irClass.declarations) {
            when {
                declaration !is IrConstructor -> continue
                declaration.isPrimary -> primary = declaration
                declaration.isSensible -> sensible = declaration
                declaration.valueParameters.isEmpty() -> return // don't touch it if it already has an empty constructor
            }
        }

        require(primary != null) { "missing primary constructor in class ${irClass.symbol}" }
        require(sensible != null) { "missing sensible constructor in class ${irClass.symbol}" }

        buildSensibleConstructorBody(irClass, primary, sensible)

        return
    }

    val IrConstructor.isSensible
        get() = origin.let {
            it is IrDeclarationOrigin.GeneratedByPlugin && it.pluginKey == SensibleConstructorGenerator.Key
        }

    private fun buildSensibleConstructorBody(irClass: IrClass, primary: IrConstructor, sensible: IrConstructor) {

        sensible.body = irFactory.createBlockBody(SYNTHETIC_OFFSET, SYNTHETIC_OFFSET).apply {

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
