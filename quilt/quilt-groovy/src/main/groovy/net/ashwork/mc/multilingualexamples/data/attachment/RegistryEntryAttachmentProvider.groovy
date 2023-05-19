package net.ashwork.mc.multilingualexamples.data.attachment

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.mojang.serialization.JsonOps
import groovy.transform.CompileStatic
import net.minecraft.data.CachedOutput
import net.minecraft.data.DataProvider
import net.minecraft.data.PackOutput
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import org.quiltmc.qsl.registry.attachment.api.RegistryEntryAttachment

import java.util.concurrent.CompletableFuture
/**
 * A {@link DataProvider} for {@link RegistryEntryAttachment}s.
 *
 * <p>To use this provider, extend this class and implement {@link RegistryEntryAttachmentProvider#addAttachments()}.
 * Then, register an instance using {@link net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator.Pack#addProvider(net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator.Pack.Factory)}
 * through a {@link net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint}.
 *
 * <p>An attachment can be added using {@link RegistryEntryAttachmentProvider#attach(RegistryEntryAttachment)}.
 * From there, objects and tags can be chained using the associated {@link net.ashwork.mc.multilingualexamples.data.attachment.RegistryEntryAttachmentProvider.AttachmentData#addObject(Object, Object)}
 * and {@link net.ashwork.mc.multilingualexamples.data.attachment.RegistryEntryAttachmentProvider.AttachmentData#addTag(TagKey, Object)}, respectively. There are also
 * {@link ResourceLocation} alternatives for optional entries. Tags are considered
 * redundant when marked as optional, so they can never be marked as optional within
 * a provider.
 *
 * <pre>{@code
 *     @Override
 *     protected void addAttachments() {
 *         this.attach(BlockContentRegistries.FLATTENABLE)
 *             .addObject(Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE.defaultBlockState())
 *             .addTag(BlockTags.SAND, Blocks.GLASS.defaultBlockState())
 *             .addOptionalObject(
 *                 new ResourceLocation("examplemod:example_block"),
 *                 Blocks.GOLD_BLOCK.defaultBlockState()
 *             ).addTag(
 *                 new ResourceLocation("examplemod:example_tag"),
 *                 Blocks.IRON_BLOCK.defaultBlockState()
 *             );
 *
 *         // ...
 *     }
 * }</pre>
 *
 * @see RegistryEntryAttachment
 * @see DataProvider
 */
@CompileStatic
abstract class RegistryEntryAttachmentProvider implements DataProvider {

    private final List<AttachmentData<?, ?>> attachments
    private final PackOutput.PathProvider attachmentPathProvider

    /**
     * Default constructor.
     *
     * @param output the output of the data generator
     */
    protected RegistryEntryAttachmentProvider(PackOutput output) {
        this.attachmentPathProvider = output.createPathProvider(PackOutput.Target.DATA_PACK, 'attachments')
        this.attachments = []
    }

    @Override
    CompletableFuture<?> run(CachedOutput cachedOutput) {
        this.addAttachments()

        // Write the attachments to a file
        return CompletableFuture.allOf(
                this.attachments.collect {
                    it.generate(cachedOutput, this.attachmentPathProvider)
                }.toArray(CompletableFuture[]::new)
        )
    }

    /**
     * Adds all {@link RegistryEntryAttachment}s to a list to be generated.
     */
    protected abstract void addAttachments()

    @Override
    String getName() {
        return 'Registry Entry Attachments'
    }

    /**
     * Creates a new chainable attachment object that can be encoded by the
     * data generator.
     *
     * @param attachment the attachment being generated
     * @return a chainable attachment object that can be encoded
     * @param <R> the type of the entries in the registry
     * @param <V> the type of the attached value
     */
    protected <R, V> AttachmentData<R, V> attach(RegistryEntryAttachment<R, V> attachment) {
        var data = new AttachmentData<>(attachment)
        this.attachments.add(data)
        return data
    }

    /**
     * A utility class that holds the {@link RegistryEntryAttachment} data
     * to generate.
     *
     * @param <R> the type of the entries in the registry
     * @param <V> the type of the attached value
     */
    static class AttachmentData<R, V> {

        private final RegistryEntryAttachment<R, V> attachment
        private boolean replace
        private final Map<String, V> values

        private AttachmentData(RegistryEntryAttachment<R, V> attachment) {
            this.attachment = attachment
            this.replace = false
            this.values = [:]
        }

        /**
         * Notifies the parser that all previously read entries should be
         * removed.
         *
         * @return the chainable attachment object
         */
        AttachmentData<R, V> replace() {
            this.replace = true
            return this
        }

        /**
         * Adds an object with an attachable value.
         *
         * @param obj the object being attached to
         * @param value the value attached to the object
         * @return the chainable attachment object
         */
        AttachmentData<R, V> addObject(R obj, V value) {
            this.values[this.attachment.registry().getKey(obj).toString()] = value
            return this
        }

        /**
         * Adds an object that may not be present by its {@link ResourceLocation}
         * with an attachable value.
         *
         * @param obj the name of the object being attached to
         * @param value the value attached to the object
         * @return the chainable attachment object
         */
        AttachmentData<R, V> addOptionalObject(ResourceLocation obj, V value) {
            this.values["$obj?".toString()] = value
            return this
        }

        /**
         * Adds a tag by its {@link TagKey} with an attachable value.
         *
         * @param tag the tag being attached to
         * @param value the value attached to the object
         * @return the chainable attachment object
         */
        AttachmentData<R, V> addTag(TagKey<R> tag, V value) {
            return this.addTag(tag.location(), value)
        }

        /**
         * Adds a tag by its {@link ResourceLocation} with an attachable value.
         *
         * @param tag the name of the tag being attached to
         * @param value the value attached to the object
         * @return the chainable attachment object
         */
        AttachmentData<R, V> addTag(ResourceLocation tag, V value) {
            this.values["#$tag".toString()] = value
            return this
        }

        /**
         * Generates and writes the attachment to a file.
         *
         * @param output the output cache to notify changes for
         * @param attachmentPathProvider the path provider to the {@code attachments} directory
         * @return a future generating and writing the file
         */
        CompletableFuture<?> generate(CachedOutput output, PackOutput.PathProvider attachmentPathProvider) {
            var obj = new JsonObject()
            obj.addProperty("replace", this.replace)

            var vals = new JsonObject()
            this.values.forEach { String key, V value ->
                var encoded = this.attachment.codec().encodeStart(JsonOps.INSTANCE, value).getOrThrow(false) {} as JsonElement
                vals.add(key, encoded)
            }
            obj.add("values", vals)

            return DataProvider.saveStable(output, obj, attachmentPathProvider.json(new ResourceLocation(
                    this.attachment.id().namespace,
                    "${this.attachment.registry().key().location().toString().replaceAll(':', '/')}/${this.attachment.id().path}"
            )))
        }
    }
}
