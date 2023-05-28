/*
 * Copyright © 2020-2021, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
plugins {
    kotlin("multiplatform") version "1.8.21"
    id("hu.simplexion.z2.sensible") version "0.1.0-SNAPSHOT"
    application
}

repositories {
    mavenLocal()
    mavenCentral()
}

sensible {
    dumpPoints.set(listOf("before", "after"))
    printDumps.set(true)
    pluginLogDir.set("$projectDir/tmp/log")
}

kotlin {
    jvm {}
    js(IR) {
        browser()
        binaries.library()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("hu.simplexion.z2.sensible:z2-sensible-runtime:0.1.0-SNAPSHOT")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}