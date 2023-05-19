package net.ashwork.mc.multilingualexamples.data.attachment

import net.ashwork.mc.multilingualexamples.registrar.SQUISHED_WAFFLE
import net.ashwork.mc.multilingualexamples.registrar.WAFFLE
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import org.quiltmc.qsl.block.content.registry.api.BlockContentRegistries

/**
 * A data provider which generates registry entry attachments for this mod.
 *
 * @param output the output of the data generator
 */
class ExampleRegistryEntryAttachmentProvider(output: FabricDataOutput): RegistryEntryAttachmentProvider(output) {
    override fun addAttachments() {
        this.attach(BlockContentRegistries.FLATTENABLE)
            .addObject(WAFFLE, SQUISHED_WAFFLE.defaultBlockState())
    }
}
