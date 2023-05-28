package hu.simplexion.z2.sensible.kotlin.ir


import hu.simplexion.z2.sensible.kotlin.ir.plugin.DumpPoint

/**
 * For plugin debugging when the developer runs unit tests manually. Should not be committed.
 */
fun forPluginDevelopment(pluginLogDir: String? = null) = // for manual running of unit tests
    listOf(
        hu.simplexion.z2.sensible.kotlin.SensibleCompilerPluginRegistrar(
            emptyList(),
            DumpPoint.values().toList(),
            printDumps = true,
            pluginLogDir
        )
    )

/**
 * For unit tests that should produce a comparable trace for validation.
 */
fun forValidatedResult(pluginLogDir: String? = null) = // development settings
    listOf(
        hu.simplexion.z2.sensible.kotlin.SensibleCompilerPluginRegistrar(
            dumpPoints = if (pluginLogDir == null) emptyList() else DumpPoint.values().toList(),
            pluginLogDir = pluginLogDir
        )
    )

fun forProduction(pluginLogDir: String? = null) =
    listOf(
        hu.simplexion.z2.sensible.kotlin.SensibleCompilerPluginRegistrar(
            dumpPoints = if (pluginLogDir == null) emptyList() else DumpPoint.values().toList(),
            pluginLogDir = pluginLogDir
        )
    )

/**
 * For unit tests that are supposed to generate an error.
 */
fun forCompilationError(pluginLogDir: String? = null) =
    listOf(
        hu.simplexion.z2.sensible.kotlin.SensibleCompilerPluginRegistrar(
            pluginLogDir = pluginLogDir
        )
    )
