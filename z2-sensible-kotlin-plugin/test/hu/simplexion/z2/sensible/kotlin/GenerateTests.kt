package hu.simplexion.z2.sensible.kotlin

import hu.simplexion.z2.sensible.kotlin.runners.AbstractBoxTest
import hu.simplexion.z2.sensible.kotlin.runners.AbstractDiagnosticTest
import org.jetbrains.kotlin.generators.generateTestGroupSuiteWithJUnit5

fun main() {
    generateTestGroupSuiteWithJUnit5 {
        testGroup(testDataRoot = "testData", testsRoot = "test-gen") {

            testClass<AbstractDiagnosticTest> {
                model("diagnostics")
            }

            testClass<AbstractBoxTest> {
                model("box")
            }

        }
    }
}