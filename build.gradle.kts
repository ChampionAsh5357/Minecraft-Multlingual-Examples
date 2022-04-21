import java.util.Calendar
import org.cadixdev.gradle.licenser.Licenser
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.time.Instant
import java.time.format.DateTimeFormatter

// Add plugins to fix gradle issues
//plugins {
//    idea
//    alias(libs.plugins.kotlin.jvm) apply false
//}

// Add common plugins
plugins {
    java
    alias(libs.plugins.licenser)
    alias(libs.plugins.kotlin.jvm) apply false
    idea
}

// Cache properties
internal val projectId: String = extra["base.id"] as String
internal val projectName: String = extra["base.name"] as String
internal val projectVersion: String = extra["base.version"] as String
internal val author: String = extra["base.author"] as String

internal val artifactGroup: String = extra["base.group"] as String
internal val javaVersion: String = extra["java.version"] as String
internal val fileEncoding: String = extra["file.encoding"] as String

internal val licenseHeader: TextResource = resources.text.fromFile(rootProject.file("HEADER"))
internal val licenseId: String = extra["license.id"] as String
internal val startYear: String = extra["base.year.start"] as String
internal val currentYear: Int = Calendar.getInstance().get(Calendar.YEAR)

// For all subprojects
subprojects {
    // Java Settings
    plugins.withType<JavaPlugin> {
        group = artifactGroup

        java {
            toolchain.languageVersion.set(JavaLanguageVersion.of(javaVersion))

            withSourcesJar()
            withJavadocJar()
        }

        tasks.withType<JavaCompile> {
            options.encoding = fileEncoding
        }

        tasks.javadoc {
            options {
                encoding = fileEncoding
                if (this is StandardJavadocDocletOptions) {
                    tags(
                        "apiNote:a:API Note:",
                        "implSpec:a:Implementation Requirements:",
                        "implNote:a:Implementation Note:"
                    )
                }
            }
        }

        afterEvaluate {
            tasks.jar {
                from(rootProject.file("LICENSE"))

                manifest.attributes(
                    mapOf(
                        "Specification-Title" to projectId,
                        "Specification-Vendor" to author,
                        "Specification-Version" to projectVersion,
                        "Implementation-Title" to base.archivesName.get(),
                        "Implementation-Vendor" to author,
                        "Implementation-Version" to project.version,
                        "Implementation-Timestamp" to DateTimeFormatter.ISO_INSTANT.format(Instant.now())
                    )
                )
            }

            tasks.named<Copy>("processResources") {
                inputs.property("version", project.version.toString())

                filesMatching(listOf("**/mods.toml", "**/fabric.mod.json")) {
                    expand(
                        mapOf(
                            "version" to project.version.toString()
                        )
                    )
                }

                duplicatesStrategy = DuplicatesStrategy.EXCLUDE
            }
        }
    }

    // Kotlin Settings
    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = javaVersion
    }

    // License Settings
    plugins.withType<Licenser> {
        license {
            header.set(licenseHeader)

            properties {
                set("projectName", projectName)
                set("startYear", startYear)
                set("year", currentYear)
                set("author", author)
                set("license", licenseId)
            }

            include("**/*.java")
            include("**/*.kt")
            include("**/*.scala")
            include("**/*.groovy")
        }
    }
}
