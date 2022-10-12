// Add plugins
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.licenser)
    alias(libs.plugins.forge.gradle)
}

// Common Properties
internal val modId: String = extra["mod.id"] as String
internal val rootId: String = extra["base.id"] as String
internal val loaderId: String = extra["forge.id"] as String
internal val languageId: String = extra["kotlin.id"] as String

internal val commonResources: File = rootProject.file("shared-resources/common")
internal val generatedResources: File = rootProject.file("shared-resources/generated")

// Set subproject data
base.archivesName.set("${rootId}-${loaderId}-${languageId}")
version = "${extra["base.version"]}.${extra["${loaderId}.${languageId}.version"]}"

// Include shared resources
sourceSets["main"].resources {
    srcDirs(
        commonResources,
        generatedResources,
        rootProject.file("shared-resources/forge")
    )
    exclude(".cache/")
}

// Add dependencies
dependencies {
    minecraft(libs.forge)

    minecraftLibrary(libs.kotlin.stdlib) {
        // Exclude annotations to avoid overriding issues with modules during runtime
        exclude(group = "org.jetbrains", module = "annotations")
    }
}

// Setup runs
minecraft {
    mappings(extra["forge.mappings.channel"] as String, extra["forge.mappings.version"] as String)

    runs {
        create("client") {
            workingDirectory(file("../run/client"))
        }

        create("server") {
            workingDirectory(file("../run/server"))
        }

        create("gameTestServer") {
            workingDirectory(file("../run/game-test-server"))
        }

        create("data") {
            workingDirectory(file("../run/data"))

            args(
                "--mod", modId,
                "--all",
                "--output", generatedResources
            )
            sourceSets["main"].resources.srcDirs.forEach {
                args("--existing", it)
            }
        }
    }

    runs.all {
        ideaModule = "${rootId}.${loaderId}.${loaderId}-${languageId}.main"
        property("forge.enabledGameTestNamespaces", modId)
        mods.create(modId).source(sourceSets["main"])
    }
}

// Reobfuscate the jar file to be used in production
tasks.jar {
    finalizedBy("reobfJar")
}