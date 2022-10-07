pluginManagement {
    // Add repositories to grab plugins from
    repositories {
        gradlePluginPortal()
        maven {
            name = "Forge"
            url = uri("https://maven.minecraftforge.net")
        }
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net")
        }
        maven {
            name  = "Quilt"
            url = uri("https://maven.quiltmc.org/repository/release")
        }
    }
}

// Project data
rootProject.name = extra["base.id"] as String

// Supported Loaders
internal val loaders: List<String> = listOf(
    extra["forge.id"] as String,
    extra["fabric.id"] as String,
    extra["quilt.id"] as String
)

// Supported Languages
internal val languages: List<String> = listOf(
    extra["java.id"] as String,
    extra["kotlin.id"] as String,
    extra["scala.id"] as String,
    extra["groovy.id"] as String
)

// Include Loader/Language pair
loaders.forEach { loader -> languages.forEach { language -> include("${loader}:${loader}-${language}") } }
