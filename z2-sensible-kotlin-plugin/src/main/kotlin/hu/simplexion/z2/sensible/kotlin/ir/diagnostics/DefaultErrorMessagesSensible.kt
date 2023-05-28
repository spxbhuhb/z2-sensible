/*
 * Copyright © 2020-2021, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package hu.simplexion.z2.sensible.kotlin.ir.diagnostics

import org.jetbrains.kotlin.diagnostics.rendering.DefaultErrorMessages
import org.jetbrains.kotlin.diagnostics.rendering.DiagnosticFactoryToRendererMap

object DefaultErrorMessagesSensible : DefaultErrorMessages.Extension {

    private val MAP = DiagnosticFactoryToRendererMap("AnnotationProcessing")
    override fun getMap() = MAP

}
