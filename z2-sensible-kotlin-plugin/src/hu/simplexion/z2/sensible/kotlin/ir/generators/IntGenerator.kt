package hu.simplexion.z2.sensible.kotlin.ir.generators

import hu.simplexion.z2.sensible.kotlin.ir.SensiblePluginContext

class IntGenerator(
    override val context: SensiblePluginContext
) : SensibleGenerator {

    override fun expression() = irConst(0)
}