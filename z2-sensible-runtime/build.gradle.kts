/*
 * Copyright © 2020-2023, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
plugins {
    kotlin("multiplatform")
    id("com.github.gmazzo.buildconfig")
    signing
    `maven-publish`
}

val String.propValue
    get() = (System.getenv(this.toUpperCase().replace('.', '_')) ?: project.findProperty(this))?.toString() ?: ""

val isPublishing = "z2.publish".propValue
val publishSnapshotUrl = "z2.publish.snapshot.url".propValue
val publishReleaseUrl = "z2.publish.release.url".propValue
val publishUsername = "z2.publish.username".propValue
val publishPassword = "z2.publish.password".propValue
val isSnapshot = "SNAPSHOT" in project.version.toString()

kotlin {

    jvm {
        jvmToolchain(11)
    }

    js(IR) {
        browser()
        nodejs()
        binaries.library()
    }

    sourceSets {
        commonTest {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(kotlin("test-junit"))
            }
        }
    }
}

buildConfig {
    packageName("hu.simplexion.z2.sensible.runtime")
    buildConfigField("String", "PLUGIN_VERSION", "\"${project.version}\"")
}

val baseName = "z2-sensible-runtime"
val pomName = "Z2 Sensible Runtime"
val scmPath = "spxbhuhb/z2-sensible"

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

signing {
    if (project.properties["signing.keyId"] == null) {
        useGpgCmd()
    }
    sign(publishing.publications)
}

publishing {

    repositories {
        maven {
            name = "MavenCentral"
            url = project.uri(requireNotNull(if (isSnapshot) publishSnapshotUrl else publishReleaseUrl))
            credentials {
                username = publishUsername
                password = publishPassword
            }
        }
    }

    publications.withType<MavenPublication>().all {

        artifact(javadocJar.get())

        pom {
            description.set("Runtime of Sensible, a Kotlin compiler plugin that generates empty constructors with sensible default values.")
            name.set(pomName)
            url.set("https://github.com/$scmPath")
            scm {
                url.set("https://github.com/$scmPath")
                connection.set("scm:git:git://github.com/$scmPath.git")
                developerConnection.set("scm:git:ssh://git@github.com/$scmPath.git")
            }
            licenses {
                license {
                    name.set("Apache 2.0")
                    url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    distribution.set("repo")
                }
            }
            developers {
                developer {
                    id.set("toth-istvan-zoltan")
                    name.set("Tóth István Zoltán")
                    url.set("https://github.com/toth-istvan-zoltan")
                    organization.set("Simplexion Kft.")
                    organizationUrl.set("https://www.simplexion.hu")
                }
            }
        }
    }
}