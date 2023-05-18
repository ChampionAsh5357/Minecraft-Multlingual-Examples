/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data.attachment;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.qsl.registry.attachment.api.RegistryEntryAttachment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A {@link DataProvider} for {@link RegistryEntryAttachment}s.
 *
 * <p>To use this provider, extend this class and implement {@link #addAttachments()}.
 * Then, register an instance using {@link DataGenerator#addProvider(boolean, DataProvider)}
 * through a {@link net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint}.
 *
 * <p>An attachment can be added using {@link #attach(RegistryEntryAttachment)}.
 * From there, objects and tags can be chained using the associated {@link AttachmentData#addObject(Object, Object)}
 * and {@link AttachmentData#addTag(TagKey, Object)}, respectively. There are also
 * {@link ResourceLocation} alternatives for optional entries. Tags are considered
 * redundant when marked as optional, so they can never be marked as optional within
 * a provider.
 *
 * <pre>{@code
 *     @Override
 *     public void addAttachments() {
 *         this.attach(BlockContentRegistries.FLATTENABLE_BLOCK)
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
 * @see DataGenerator
 */
public abstract class RegistryEntryAttachmentProvider implements DataProvider {

    private final List<AttachmentData<?, ?>> attachments;
    private final DataGenerator.PathProvider attachmentPathProvider;

    /**
     * Default constructor.
     *
     * @param generator the generator being written to
     */
    protected RegistryEntryAttachmentProvider(DataGenerator generator) {
        this.attachmentPathProvider = generator.createPathProvider(DataGenerator.Target.DATA_PACK, "attachments");
        this.attachments = new ArrayList<>();
    }

    /**
     * Adds all {@link RegistryEntryAttachment}s to a list to be generated.
     */
    protected abstract void addAttachments();

    @Override
    public void run(@NotNull CachedOutput cachedOutput) throws IOException {
        this.addAttachments();

        // Write the attachments to a file
        for (var attachment : this.attachments) {
            attachment.generate(cachedOutput, this.attachmentPathProvider);
        }
    }

    @NotNull
    @Override
    public String getName() {
        return "Registry Entry Attachments";
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
    public <R, V> AttachmentData<R, V> attach(RegistryEntryAttachment<R, V> attachment) {
        var data = new AttachmentData<>(attachment);
        this.attachments.add(data);
        return data;
    }

    /**
     * A utility class that holds the {@link RegistryEntryAttachment} data
     * to generate.
     *
     * @param <R> the type of the entries in the registry
     * @param <V> the type of the attached value
     */
    public static class AttachmentData<R, V> {

        private final RegistryEntryAttachment<R, V> attachment;
        private boolean replace;
        private final Map<String, V> values;

        private AttachmentData(RegistryEntryAttachment<R, V> attachment) {
            this.attachment = attachment;
            this.replace = false;
            this.values = new HashMap<>();
        }

        /**
         * Notifies the parser that all previously read entries should be
         * removed.
         *
         * @return the chainable attachment object
         */
        public AttachmentData<R, V> replace() {
            this.replace = true;
            return this;
        }

        /**
         * Adds an object with an attachable value.
         *
         * @param obj the object being attached to
         * @param value the value attached to the object
         * @return the chainable attachment object
         */
        public AttachmentData<R, V> addObject(R obj, V value) {
            this.values.put(Objects.requireNonNull(this.attachment.registry().getKey(obj)).toString(), value);
            return this;
        }

        /**
         * Adds an object that may not be present by its {@link ResourceLocation}
         * with an attachable value.
         *
         * @param obj the name of the object being attached to
         * @param value the value attached to the object
         * @return the chainable attachment object
         */
        public AttachmentData<R, V> addOptionalObject(ResourceLocation obj, V value) {
            this.values.put(obj.toString() + "?", value);
            return this;
        }

        /**
         * Adds a tag by its {@link TagKey} with an attachable value.
         *
         * @param tag the tag being attached to
         * @param value the value attached to the object
         * @return the chainable attachment object
         */
        public AttachmentData<R, V> addTag(TagKey<R> tag, V value) {
            return this.addTag(tag.location(), value);
        }

        /**
         * Adds a tag by its {@link ResourceLocation} with an attachable value.
         *
         * @param tag the name of the tag being attached to
         * @param value the value attached to the object
         * @return the chainable attachment object
         */
        public AttachmentData<R, V> addTag(ResourceLocation tag, V value) {
            this.values.put("#" + tag.toString(), value);
            return this;
        }

        /**
         * Generates and writes the attachment to a file.
         *
         * @param output the output cache to notify changes for
         * @param attachmentPathProvider the path provider to the {@code attachments} directory
         * @throws IOException if an error occurs when writing the file
         */
        public void generate(CachedOutput output, DataGenerator.PathProvider attachmentPathProvider) throws IOException {
            var obj = new JsonObject();
            obj.addProperty("replace", this.replace);

            var vals = new JsonObject();
            this.values.forEach((key, value) -> {
                var encoded = this.attachment.codec().encodeStart(JsonOps.INSTANCE, value).getOrThrow(false, str -> {});
                vals.add(key, encoded);
            });
            obj.add("values", vals);

            DataProvider.saveStable(output, obj, attachmentPathProvider.json(new ResourceLocation(
                    this.attachment.id().getNamespace(),
                    this.attachment.registry().key().location().toString().replace(":", "/") + "/" + this.attachment.id().getPath()
            )));
        }
    }
}
