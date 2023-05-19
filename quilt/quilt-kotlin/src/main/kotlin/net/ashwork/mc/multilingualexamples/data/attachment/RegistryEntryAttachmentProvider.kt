/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data.attachment

import com.google.gson.JsonObject
import com.mojang.serialization.JsonOps
import net.minecraft.data.CachedOutput
import net.minecraft.data.DataProvider
import net.minecraft.data.PackOutput
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import org.quiltmc.qsl.registry.attachment.api.RegistryEntryAttachment
import java.util.concurrent.CompletableFuture

/**
 * A [DataProvider] for [RegistryEntryAttachment]s.
 *
 * To use this provider, extend this class and implement [addAttachments].
 * Then, register an instance using [net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator.Pack.addProvider]
 * through a [net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint].
 *
 * An attachment can be added using [attach].
 * From there, objects and tags can be chained using the associated [AttachmentData.addObject]
 * and [AttachmentData.addTag], respectively. There are also
 * [ResourceLocation] alternatives for optional entries. Tags are considered
 * redundant when marked as optional, so they can never be marked as optional within
 * a provider.
 *
 * ```kotlin
 * override fun addAttachments() {
 *     this.attach(BlockContentRegistries.FLATTENABLE)
 *         .addObject(Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE.defaultBlockState())
 *         .addTag(BlockTags.SAND, Blocks.GLASS.defaultBlockState())
 *         .addOptionalObject(
 *             ResourceLocation("examplemod:example_block"),
 *             Blocks.GOLD_BLOCK.defaultBlockState()
 *         ).addTag(
 *             ResourceLocation("examplemod:example_tag"),
 *             Blocks.IRON_BLOCK.defaultBlockState()
 *         )
 *
 *     // ...
 * }
 * ```
 *
 * @param output the output of the data generator
 *
 * @see RegistryEntryAttachment
 * @see DataProvider
 */
abstract class RegistryEntryAttachmentProvider(output: PackOutput): DataProvider {

    private val attachmentPathProvider: PackOutput.PathProvider
    private val attachments: MutableList<AttachmentData<*, *>>

    init {
        this.attachmentPathProvider = output.createPathProvider(PackOutput.Target.DATA_PACK, "attachments")
        this.attachments = mutableListOf()
    }

    /**
     * Adds all [RegistryEntryAttachment]s to a list to be generated.
     */
    abstract fun addAttachments()

    override fun run(cachedOutput: CachedOutput): CompletableFuture<*> {
        this.addAttachments()

        // Write the attachments to a file
        return CompletableFuture.allOf(
            *this.attachments
                .map { it.generate(cachedOutput, this.attachmentPathProvider) }
                .toTypedArray()
        )
    }

    override fun getName(): String = "Registry Entry Attachments"

    /**
     * Creates a new chainable attachment object that can be encoded by the
     * data generator.
     *
     * @param attachment the attachment being generated
     * @param R the type of the entries in the registry
     * @param V the type of the attached value
     * @return a chainable attachment object that can be encoded
     */
    protected fun <R, V> attach(attachment: RegistryEntryAttachment<R, V>): AttachmentData<R, V> = AttachmentData(attachment).also {
        this.attachments.add(it)
    }
}

/**
 * A utility class that holds the [RegistryEntryAttachment] data
 * to generate.
 *
 * @param attachment the attachment being generated
 * @param R the type of the entries in the registry
 * @param V the type of the attached value
 */
class AttachmentData<R, V>(private val attachment: RegistryEntryAttachment<R, V>) {

    private var replace: Boolean = false
    private val values: MutableMap<String, V> = mutableMapOf()

    /**
     * Notifies the parser that all previously read entries should be
     * removed.
     *
     * @return the chainable attachment object
     */
    fun replace(): AttachmentData<R, V> = apply { this.replace = true }

    /**
     * Adds an object with an attachable value.
     *
     * @param obj the object being attached to
     * @param value the value attached to the object
     * @return the chainable attachment object
     */
    fun addObject(obj: R, value: V): AttachmentData<R, V> = apply {
        this.values[this.attachment.registry().getKey(obj)!!.toString()] = value
    }

    /**
     * Adds an object that may not be present by its [ResourceLocation]
     * with an attachable value.
     *
     * @param obj the name of the object being attached to
     * @param value the value attached to the object
     * @return the chainable attachment object
     */
    fun addOptionalObject(obj: ResourceLocation, value: V): AttachmentData<R, V> = apply {
        this.values["$obj?"] = value
    }

    /**
     * Adds a tag by its [TagKey] with an attachable value.
     *
     * @param tag the tag being attached to
     * @param value the value attached to the object
     * @return the chainable attachment object
     */
    fun addTag(tag: TagKey<R>, value: V): AttachmentData<R, V> = this.addTag(tag.location, value)

    /**
     * Adds a tag by its [ResourceLocation] with an attachable value.
     *
     * @param tag the name of the tag being attached to
     * @param value the value attached to the object
     * @return the chainable attachment object
     */
    fun addTag(tag: ResourceLocation, value: V): AttachmentData<R, V> = apply {
        this.values["#$tag"] = value
    }

    /**
     * Generates and writes the attachment to a file.
     *
     * @param output the output cache to notify changes for
     * @param attachmentPathProvider the path provider to the {@code attachments} directory
     * @return a future generating and writing the file
     */
    fun generate(output: CachedOutput, attachmentPathProvider: PackOutput.PathProvider): CompletableFuture<*> {
        val obj = JsonObject()
        obj.addProperty("replace", this.replace)

        val vals = JsonObject()
        this.values.forEach { (key, value) ->
            val encoded = this.attachment.codec().encodeStart(JsonOps.INSTANCE, value).getOrThrow(false) {}
            vals.add(key, encoded)
        }
        obj.add("values", vals)

        return DataProvider.saveStable(output, obj, attachmentPathProvider.json(ResourceLocation(
            this.attachment.id().namespace,
            "${this.attachment.registry().key().location().toString().replace(":", "/")}/${this.attachment.id().path}"
        )))
    }
}
