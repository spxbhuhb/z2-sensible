package hu.simplexion.z2.sensible.kotlin

import hu.simplexion.z2.sensible.kotlin.fir.SensibleConstructorGenerator
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar

class SensiblePluginRegistrar : FirExtensionRegistrar() {
    override fun ExtensionRegistrarContext.configurePlugin() {
        +::SensibleConstructorGenerator
    }
}