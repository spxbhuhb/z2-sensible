package hu.simplexion.z2.sensible.kotlin

import hu.simplexion.z2.sensible.kotlin.fir.EmptyConstructorGenerator
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar

class SimplePluginRegistrar : FirExtensionRegistrar() {
    override fun ExtensionRegistrarContext.configurePlugin() {
        +::EmptyConstructorGenerator
    }
}