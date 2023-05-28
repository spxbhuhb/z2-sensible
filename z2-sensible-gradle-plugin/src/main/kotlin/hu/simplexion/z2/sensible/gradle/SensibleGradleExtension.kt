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

package hu.simplexion.z2.sensible.gradle

import org.gradle.api.Action
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property

/**
 * Settings for the Z2 Sensible compiler plugin.
 */
open class SensibleGradleExtension(objects: ObjectFactory) {

    /**
     * ```
     * Category: Source code option
     * ```
     *
     * Name of the annotation that instructs the plugin add a sensible constructor.
     *
     * Default: `hu.simplexion.z2.sensible.runtime.Sensible`
     */
    val annotations: ListProperty<String> = objects.listProperty(String::class.java)

    /**
     * ```
     * Category: Plugin development
     * ```
     *
     * Points of plugin where it dumps information about the compilation (such as the IR tree)
     *
     * ```
     * dumpPoints.set(listOf("before", "after"))
     * ```
     */
    val dumpPoints: ListProperty<String> = objects.listProperty(String::class.java)

    /**
     * ```
     * Category: Plugin development
     * ```
     *
     * When `true` the plugin prints dumps specified by [dumpPoints] to the standard output instead passing them
     * to the compiler framework.
     */
    val printDumps: Property<Boolean> = objects.property(Boolean::class.java).also { it.set(false) }

    /**
     * ```
     * Category: Plugin development/troubleshooting.
     * ```
     *
     * When set the plugin saves logs into a file named "sensible-log-yyyyMMdd-HHmmss.txt" file. This is mostly
     * useful during the development of the plugin itself and/or troubleshooting. The file contains
     * the dumps specified by [dumpPoints].
     *
     * Generates a compilation warning when set because of the large amount of data it can save.
     *
     * Relative paths save the data into the gradle daemons log directory. On my machine it is:
     *
     * `/Users/<username>/Library/Application Support/kotlin/daemon`
     */
    val pluginLogDir: Property<String?> = objects.property(String::class.java).also { it.set(null as String?) }
}

@Suppress("unused")
fun org.gradle.api.Project.z2sensible(configure: Action<SensibleGradleExtension>): Unit =
    (this as org.gradle.api.plugins.ExtensionAware).extensions.configure("sensible", configure)
