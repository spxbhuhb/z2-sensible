/*
 * Copyright © 2020-2023, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.z2.sensible.kotlin.ir.plugin

import hu.simplexion.z2.sensible.kotlin.ir.PluginContext

enum class DumpPoint(
    val optionValue: String
) {
    Before("before"),
    After("after");

    fun dump(pluginContext: PluginContext, dumpFunc: () -> Unit) {
        if (this in pluginContext.dumpPoints) dumpFunc()
    }

    companion object {
        fun optionValues(): List<String> = values().map { it.optionValue }
        fun fromOption(value: String): DumpPoint? = values().firstOrNull { it.optionValue == value }
    }
}