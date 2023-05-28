/*
 * Copyright © 2020-2021, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.z2.sensible.kotlin.ir.plugin

import hu.simplexion.z2.sensible.kotlin.ir.generators.Generator

data class SensibleOptions(
    val annotations: List<String>,
    val generators: List<Generator>,
    val dumpPoints: List<DumpPoint>,
    val printDumps : Boolean,
    val pluginLogDir: String?
)