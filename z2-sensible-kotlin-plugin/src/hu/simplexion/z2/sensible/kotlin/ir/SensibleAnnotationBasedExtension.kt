/*
 * Copyright © 2022-2023, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.z2.sensible.kotlin.ir

import org.jetbrains.kotlin.extensions.AnnotationBasedExtension
import org.jetbrains.kotlin.ir.declarations.IrClass
import org.jetbrains.kotlin.ir.descriptors.toIrBasedDescriptor

interface SensibleAnnotationBasedExtension : AnnotationBasedExtension {

    fun IrClass.isAnnotatedWithSensible(): Boolean =
        toIrBasedDescriptor().hasSpecialAnnotation(null)

}