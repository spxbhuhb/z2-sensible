/*
 * Copyright © 2020-2021, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.z2.sensible.kotlin.ir.plugin

import hu.simplexion.z2.sensible.kotlin.ir.generators.SensibleGenerator

data class SensibleOptions(
    val annotations: List<String>,
    val generators: List<SensibleGenerator>,
    val dumpPoints: List<SensibleDumpPoint>,
    val printDumps : Boolean,
    val pluginLogDir: String?
)