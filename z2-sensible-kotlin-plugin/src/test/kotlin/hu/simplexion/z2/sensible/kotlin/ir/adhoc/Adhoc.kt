package hu.simplexion.z2.sensible.kotlin.ir.adhoc

import hu.simplexion.z2.sensible.runtime.Sensible

@Sensible
class AdhocInt(
    val i : Int
)

class AdhocIntManual(
    val i : Int
) {
    constructor() : this(0)
}