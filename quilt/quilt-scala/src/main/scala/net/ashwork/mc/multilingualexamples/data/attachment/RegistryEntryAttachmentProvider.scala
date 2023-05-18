package net.ashwork.mc.multilingualexamples.data.attachment

import com.google.gson.JsonObject
import com.mojang.serialization.JsonOps
import net.minecraft.data.DataGenerator.{PathProvider, Target}
import net.minecraft.data.{CachedOutput, DataGenerator, DataProvider}
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import org.quiltmc.qsl.registry.attachment.api.RegistryEntryAttachment

import java.io.IOException
import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
 * A [[DataProvider]] for [[RegistryEntryAttachment]]s.
 *
 * To use this provider, extend this class and implement [[addAttachments]].
 * Then, register an instance using [[DataGenerator.addProvider]]
 * through a [[net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint]].
 *
 * An attachment can be added using [[attach]].
 * From there, objects and tags can be chained using the associated [[AttachmentData.addObject]]
 * and [[AttachmentData.addTag]], respectively. There are also
 * [[ResourceLocation]] alternatives for optional entries. Tags are considered
 * redundant when marked as optional, so they can never be marked as optional within
 * a provider.
 *
 * {{{
 * override def addAttachments(): Unit = {
 *     this.attach(BlockContentRegistries.FLATTENABLE_BLOCK)
 *         .addObject(Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE.defaultBlockState)
 *         .addTag(BlockTags.SAND, Blocks.GLASS.defaultBlockState)
 *         .addOptionalObject(
 *             ResourceLocation("examplemod:example_block"),
 *             Blocks.GOLD_BLOCK.defaultBlockState
 *         ).addTag(
 *             ResourceLocation("examplemod:example_tag"),
 *             Blocks.IRON_BLOCK.defaultBlockState
 *         )
 *
 *     // ...
 * }
 * }}}
 *
 * @param generator the generator being written to
 *
 * @see RegistryEntryAttachment
 * @see DataProvider
 * @see DataGenerator
 */
abstract class RegistryEntryAttachmentProvider(generator: DataGenerator) extends DataProvider {

    private val attachmentPathProvider: PathProvider = generator.createPathProvider(Target.DATA_PACK, "attachments")
    private val attachments: ListBuffer[AttachmentData[?, ?]] = ListBuffer()

    /**
     * Adds all [[RegistryEntryAttachment]]s to a list to be generated.
     */
    def addAttachments(): Unit

    @throws(classOf[IOException])
    override def run(cachedOutput: CachedOutput): Unit = {
        this.addAttachments()

        // Write the attachments to a file
        this.attachments.foreach(_.generate(cachedOutput, this.attachmentPathProvider))
    }

    override def getName: String = "Registry Entry Attachments"

    /**
     * Creates a new chainable attachment object that can be encoded by the
     * data generator.
     *
     * @param attachment the attachment being generated
     * @tparam R         the type of the entries in the registry
     * @tparam V         the type of the attached value
     * @return a chainable attachment object that can be encoded
     */
    protected def attach[R, V](attachment: RegistryEntryAttachment[R, V]): AttachmentData[R, V] = {
        val data = AttachmentData(attachment)
        this.attachments.addOne(data)
        data
    }
}

/**
 * A utility class that holds the [[RegistryEntryAttachment]] data
 * to generate.
 *
 * @param attachment the attachment being generated
 * @tparam R the type of the entries in the registry
 * @tparam V the type of the attached value
 */
class AttachmentData[R, V](private val attachment: RegistryEntryAttachment[R, V]) {

    private var _replace: Boolean = false
    private val values: mutable.HashMap[String, V] = mutable.HashMap()

    /**
     * Notifies the parser that all previously read entries should be
     * removed.
     *
     * @return the chainable attachment object
     */
    def replace(): AttachmentData[R, V] = {
        this._replace = true
        this
    }

    /**
     * Adds an object with an attachable value.
     *
     * @param obj   the object being attached to
     * @param value the value attached to the object
     * @return the chainable attachment object
     */
    def addObject(obj: R, value: V): AttachmentData[R, V] = {
        this.values += (this.attachment.registry.getKey(obj).toString -> value)
        this
    }

    /**
     * Adds an object that may not be present by its [[ResourceLocation]]
     * with an attachable value.
     *
     * @param obj   the name of the object being attached to
     * @param value the value attached to the object
     * @return the chainable attachment object
     */
    def addOptionalObject(obj: ResourceLocation, value: V): AttachmentData[R, V] = {
        this.values += (s"$obj?" -> value)
        this
    }

    /**
     * Adds a tag by its [[TagKey]] with an attachable value.
     *
     * @param tag   the tag being attached to
     * @param value the value attached to the object
     * @return the chainable attachment object
     */
    def addTag(tag: TagKey[R], value: V): AttachmentData[R, V] = this.addTag(tag.location, value)

    /**
     * Adds a tag by its [[ResourceLocation]] with an attachable value.
     *
     * @param tag   the name of the tag being attached to
     * @param value the value attached to the object
     * @return the chainable attachment object
     */
    def addTag(tag: ResourceLocation, value: V): AttachmentData[R, V] = {
        this.values += (s"#$tag" -> value)
        this
    }

    /**
     * Generates and writes the attachment to a file.
     *
     * @param output                 the output cache to notify changes for
     * @param attachmentPathProvider the path provider to the [[attachments]] directory
     * @throws IOException if an error occurs when writing the file
     */
    @throws(classOf[IOException])
    def generate(output: CachedOutput, attachmentPathProvider: PathProvider): Unit = {
        val obj = JsonObject()
        obj.addProperty("replace", this._replace)

        val vals = JsonObject()
        this.values.foreach((key, value) => {
            val encoded = this.attachment.codec.encodeStart(JsonOps.INSTANCE, value).getOrThrow(false, _ => {})
            vals.add(key, encoded)
        })
        obj.add("values", vals)

        DataProvider.saveStable(output, obj, attachmentPathProvider.json(ResourceLocation(
            this.attachment.id.getNamespace,
            s"${this.attachment.registry.key.location.toString.replace(':', '/')}/${this.attachment.id.getPath}"
        )))
    }
}
