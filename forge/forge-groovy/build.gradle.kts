// Add plugins
plugins {
    groovy
    alias(libs.plugins.licenser)
    alias(libs.plugins.forge.gradle)
}

// Common Properties
internal val modId: String = extra["mod.id"] as String
internal val rootId: String = extra["base.id"] as String
internal val loaderId: String = extra["forge.id"] as String
internal val languageId: String = extra["groovy.id"] as String

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

// Add library configurations for non-mod dependencies
internal val libraryConfiguration = configurations.create("library")
configurations.named("implementation") {
    extendsFrom(libraryConfiguration)
}

// Add dependencies
dependencies {
    minecraft(libs.forge)

    libraryConfiguration(libs.groovy.core)
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

        lazyToken("minecraft_classpath") {
            libraryConfiguration.copyRecursive().resolve().joinToString(separator = File.pathSeparator) { it.absolutePath }
        }
    }
}

// Reobfuscate the jar file to be used in production
tasks.jar {
    finalizedBy("reobfJar")
}
