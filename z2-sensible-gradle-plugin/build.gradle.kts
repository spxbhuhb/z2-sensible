/*
 * Copyright © 2020-2023, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
plugins {
    id("com.gradle.plugin-publish")
    kotlin("jvm")
    signing
    `maven-publish`
}

val String.propValue
    get() = (System.getenv(this.toUpperCase().replace('.', '_')) ?: project.findProperty(this))?.toString() ?: ""

val publishSnapshotUrl = "z2.publish.snapshot.url".propValue
val publishReleaseUrl = "z2.publish.release.url".propValue
val publishUsername = "z2.publish.username".propValue
val publishPassword = "z2.publish.password".propValue
val isSnapshot = "SNAPSHOT" in project.version.toString()

kotlin {
    jvmToolchain(11)
}

dependencies {
    implementation(kotlin("gradle-plugin-api"))
    implementation(project(":z2-sensible-runtime"))
}

gradlePlugin {
    website.set("https://github.com/spxbhuhb/z2-sensible")
    vcsUrl.set("https://github.com/spxbhuhb/z2-sensible.git")
    plugins {
        create("sensible") {
            id = "hu.simplexion.z2.sensible"
            displayName = "Z2 Sensible Gradle Plugin"
            description = "Kotlin compiler plugin that generates empty constructors with sensible default values."
            implementationClass = "hu.simplexion.z2.sensible.gradle.SensibleGradlePlugin"
            tags.set(listOf("kotlin"))
        }
    }
}

signing {
    if (project.properties["signing.keyId"] == null) {
        useGpgCmd()
    }
    sign(publishing.publications)
}

publishing {

    val scmPath = "spxbhuhb/z2-sensible"
    val pomName = "Z2 Sensible Gradle Plugin"

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
        pom {
            url.set("https://github.com/$scmPath")
            name.set(pomName)
            description.set("Kotlin compiler plugin that generates empty constructors with sensible default values.")
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