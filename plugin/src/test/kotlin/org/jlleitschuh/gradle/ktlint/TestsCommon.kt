package org.jlleitschuh.gradle.ktlint

import org.intellij.lang.annotations.Language
import java.io.File

const val LOWEST_SUPPORTED_GRADLE_VERSION = "6.5.1"

fun File.buildFile() = resolve("build.gradle")

fun File.ktlintBuildDir() = resolve("build/ktlint")

@Language("Groovy")
private fun pluginsBlockWithMainPluginAndKotlinPlugin(
    kotlinPluginId: String,
    kotlinVersion: String? = null
) =
    """
        plugins {
            id '$kotlinPluginId'${if (kotlinVersion != null) " version '$kotlinVersion'" else ""}
            id 'org.jlleitschuh.gradle.ktlint'
        }
    """.trimIndent()

fun File.defaultProjectSetup(kotlinVersion: String? = null) {
    kotlinPluginProjectSetup("org.jetbrains.kotlin.jvm", kotlinVersion)
}

fun File.kotlinPluginProjectSetup(
    kotlinPluginId: String,
    kotlinPluginVersion: String? = null
) {
    //language=Groovy
    buildFile().writeText(
        """
            ${pluginsBlockWithMainPluginAndKotlinPlugin(kotlinPluginId, kotlinPluginVersion)}
            
            repositories {
                gradlePluginPortal()
            }
        """.trimIndent()
    )
}
