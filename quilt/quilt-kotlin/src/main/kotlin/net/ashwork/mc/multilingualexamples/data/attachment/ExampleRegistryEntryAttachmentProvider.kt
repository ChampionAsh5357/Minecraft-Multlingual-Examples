package net.ashwork.mc.multilingualexamples.data.attachment

import net.ashwork.mc.multilingualexamples.registrar.SQUISHED_WAFFLE
import net.ashwork.mc.multilingualexamples.registrar.WAFFLE
import net.minecraft.data.DataGenerator
import org.quiltmc.qsl.block.content.registry.api.BlockContentRegistries

/**
 * A data provider which generates registry entry attachments for this mod.
 *
 * @param gen the generator being written to
 */
class ExampleRegistryEntryAttachmentProvider(gen: DataGenerator): RegistryEntryAttachmentProvider(gen) {
    override fun addAttachments() {
        this.attach(BlockContentRegistries.FLATTENABLE_BLOCK)
            .addObject(WAFFLE, SQUISHED_WAFFLE.defaultBlockState())
    }
}
