// Add plugins
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.licenser)
    alias(libs.plugins.quilt.loom)
}

// Common Properties
internal val modId: String = extra["mod.id"] as String
internal val rootId: String = extra["base.id"] as String
internal val loaderId: String = extra["quilt.id"] as String
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
    modImplementation(libs.bundles.quilt)

    implementation(libs.kotlin.stdlib)
}

// Setup runs
loom.runs {
    named("client") {
        client()
        configName = "${languageId.capitalize()} Quilt Client"
        runDir("../../run/client")
    }
    named("server") {
        server()
        configName = "${languageId.capitalize()} Quilt Server"
        runDir("../../run/server")
    }
    create("data") {
        server()
        configName = "${languageId.capitalize()} Quilt Data"
        runDir("../../run/data")
        vmArgs(
            "-Dfabric-api.datagen",
            "-Dfabric-api.datagen.output-dir=${generatedResources}",
            "-Dfabric-api.datagen.strict-validation"
        )
    }

    all {
        ideConfigGenerated(true)
    }
}

// Reobfuscate the jar file to be used in production
tasks.jar {
    finalizedBy("remapJar")
}
