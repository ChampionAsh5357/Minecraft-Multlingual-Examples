// Add plugins
plugins {
    java
    alias(libs.plugins.licenser)
    alias(libs.plugins.fabric.loom)
    alias(libs.plugins.kotlin.jvm)
}

// Common Properties
internal val modId: String = extra["mod.id"] as String
internal val rootId: String = extra["base.id"] as String
internal val loaderId: String = extra["fabric.id"] as String
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
    minecraft(libs.minecraft)
    mappings(loom.officialMojangMappings())
    modImplementation(libs.bundles.fabric)

    modImplementation(libs.fabric.kotlin)
}

// Setup runs
loom.runs {
    named("client") {
        client()
        configName = "${languageId.capitalize()} Fabric Client"
        runDir("../run/client")
    }
    named("server") {
        server()
        configName = "${languageId.capitalize()} Fabric Server"
        runDir("../run/server")
    }

    all {
        ideConfigGenerated(true)
    }
}

// Reobfuscate the jar file to be used in production
tasks.jar {
    finalizedBy("remapJar")
}
