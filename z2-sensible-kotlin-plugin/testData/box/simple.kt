package foo.bar

import hu.simplexion.z2.sensible.runtime.Sensible

@Sensible
class A(
    val i : Int
)

fun box() : String {
    return if (A().i == 0) "OK" else "Fail"
}