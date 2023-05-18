package net.ashwork.mc.multilingualexamples.data.attachment

import groovy.transform.CompileStatic
import net.ashwork.mc.multilingualexamples.registrar.BlockRegistrar
import net.minecraft.data.DataGenerator
import org.quiltmc.qsl.block.content.registry.api.BlockContentRegistries

/**
 * A data provider which generates registry entry attachments for this mod.
 */
@CompileStatic
class ExampleRegistryEntryAttachmentProvider extends RegistryEntryAttachmentProvider {

    /**
     * Default constructor.
     *
     * @param gen the gen being written to
     */
    ExampleRegistryEntryAttachmentProvider(DataGenerator gen) {
        super(gen)
    }

    @Override
    protected void addAttachments() {
        this.attach(BlockContentRegistries.FLATTENABLE_BLOCK)
            .addObject(BlockRegistrar.WAFFLE, BlockRegistrar.SQUISHED_WAFFLE.defaultBlockState())
    }
}
