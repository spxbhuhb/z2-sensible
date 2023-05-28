package hu.simplexion.z2.sensible.kotlin.ir.generators

import hu.simplexion.z2.sensible.kotlin.ir.PluginContext
import org.jetbrains.kotlin.ir.UNDEFINED_OFFSET
import org.jetbrains.kotlin.ir.expressions.IrConst
import org.jetbrains.kotlin.ir.expressions.IrConstKind
import org.jetbrains.kotlin.ir.expressions.IrExpression
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl

interface Generator {

    fun expression(): IrExpression

    val context: PluginContext

    val irContext
        get() = context.irContext

    fun irConst(value: Long): IrConst<Long> = IrConstImpl(
        UNDEFINED_OFFSET,
        UNDEFINED_OFFSET,
        irContext.irBuiltIns.longType,
        IrConstKind.Long,
        value
    )

    fun irConst(value: String): IrConst<String> = IrConstImpl(
        UNDEFINED_OFFSET,
        UNDEFINED_OFFSET,
        irContext.irBuiltIns.stringType,
        IrConstKind.String,
        value
    )

    fun irConst(value: Boolean) = IrConstImpl(
        UNDEFINED_OFFSET,
        UNDEFINED_OFFSET,
        irContext.irBuiltIns.booleanType,
        IrConstKind.Boolean,
        value
    )

    fun irNull() = IrConstImpl(
        UNDEFINED_OFFSET,
        UNDEFINED_OFFSET,
        irContext.irBuiltIns.anyNType,
        IrConstKind.Null,
        null
    )

}