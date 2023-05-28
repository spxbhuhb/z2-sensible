/*
 * Copyright © 2022-2023, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
rootProject.name = "z2-sensible"

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

include(":z2-sensible-runtime")
include(":z2-sensible-kotlin-plugin")
include(":z2-sensible-gradle-plugin")
