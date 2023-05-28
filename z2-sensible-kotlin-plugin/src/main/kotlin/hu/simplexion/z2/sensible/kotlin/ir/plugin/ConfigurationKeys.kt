/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */
package hu.simplexion.z2.sensible.kotlin.ir.plugin

import hu.simplexion.z2.sensible.kotlin.ir.generators.Generator
import hu.simplexion.z2.sensible.runtime.Plugin.OPTION_NAME_ANNOTATION
import hu.simplexion.z2.sensible.runtime.Plugin.OPTION_NAME_DUMP_POINT
import hu.simplexion.z2.sensible.runtime.Plugin.OPTION_NAME_GENERATORS
import hu.simplexion.z2.sensible.runtime.Plugin.OPTION_NAME_PLUGIN_LOG_DIR
import hu.simplexion.z2.sensible.runtime.Plugin.OPTION_NAME_PRINT_DUMPS
import org.jetbrains.kotlin.config.CompilerConfigurationKey

object ConfigurationKeys {
    val ANNOTATION: CompilerConfigurationKey<List<String>> = CompilerConfigurationKey.create(OPTION_NAME_ANNOTATION)
    val GENERATORS: CompilerConfigurationKey<List<Generator>> = CompilerConfigurationKey.create(OPTION_NAME_GENERATORS)
    val DUMP: CompilerConfigurationKey<List<DumpPoint>> = CompilerConfigurationKey.create(OPTION_NAME_DUMP_POINT)
    val PRINT_DUMPS: CompilerConfigurationKey<Boolean> = CompilerConfigurationKey.create(OPTION_NAME_PRINT_DUMPS)
    val PLUGIN_LOG_DIR: CompilerConfigurationKey<String> = CompilerConfigurationKey.create(OPTION_NAME_PLUGIN_LOG_DIR)
}
