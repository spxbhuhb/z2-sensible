/*
 * Copyright (C) 2020 Brian Norman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hu.simplexion.z2.sensible.kotlin.ir

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.Test
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.assertEquals

class AdhocTest {

    val sourceDir = "src/test/kotlin/hu/simplexion/z2/sensible/kotlin/ir/adhoc"

    @Test
    fun test() = compile("Adhoc.kt")

    @OptIn(ExperimentalCompilerApi::class)
    fun compile(fileName: String, dumpResult: Boolean = false) {

        // The test source codes are compiled by the IDE before the tests run. That compilation
        // does not apply the plugin, but the generates the class files. Those class files do not
        // contain the functionality added by the plugin, but those are the class files the class
        // loader below would load if not for the machination with the package and source file name.

        val sourceCode = Files
            .readAllBytes(Paths.get(sourceDir, fileName))
            .decodeToString()
            .replace("package hu.simplexion.z2.sensible.kotlin.ir.adhoc", "package hu.simplexion.z2.sensible.kotlin.ir.adhoc.gen")

        val result = KotlinCompilation()
            .apply {
                workingDir = File("./tmp")
                sources = listOf(
                    SourceFile.kotlin(fileName, sourceCode)
                )
                useIR = true
                compilerPluginRegistrars = forPluginDevelopment()
                inheritClassPath = true
            }
            .compile()

        assertEquals(KotlinCompilation.ExitCode.OK, result.exitCode)

        if (dumpResult) println(result.dump())

    }

    fun KotlinCompilation.Result.dump(): String {
        val lines = mutableListOf<String>()

        lines += "exitCode: ${this.exitCode}"

        lines += "======== Messages ========"
        lines += this.messages

        lines += "======== Generated files ========"
        this.generatedFiles.forEach {
            lines += it.toString()
        }

        return lines.joinToString("\n")
    }
}
