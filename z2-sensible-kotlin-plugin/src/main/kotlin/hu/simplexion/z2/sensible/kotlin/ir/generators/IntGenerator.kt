package hu.simplexion.z2.sensible.kotlin.ir.generators

import hu.simplexion.z2.sensible.kotlin.ir.PluginContext

class IntGenerator(
    override val context: PluginContext
) : Generator {

    override fun expression() = irConst(0)
}