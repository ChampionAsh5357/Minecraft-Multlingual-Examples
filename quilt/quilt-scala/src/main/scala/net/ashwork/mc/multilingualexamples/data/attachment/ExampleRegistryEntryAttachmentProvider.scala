package net.ashwork.mc.multilingualexamples.data.attachment

import net.ashwork.mc.multilingualexamples.registrar.BlockRegistrar
import net.minecraft.data.DataGenerator
import org.quiltmc.qsl.block.content.registry.api.BlockContentRegistries

/**
 * A data provider which generates registry entry attachments for this mod.
 *
 * @param gen the generator being written to
 */
class ExampleRegistryEntryAttachmentProvider(gen: DataGenerator) extends RegistryEntryAttachmentProvider(gen) {

    override def addAttachments(): Unit = {
        this.attach(BlockContentRegistries.FLATTENABLE_BLOCK)
                .addObject(BlockRegistrar.WAFFLE, BlockRegistrar.SQUISHED_WAFFLE.defaultBlockState)
    }
}
