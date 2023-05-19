package net.ashwork.mc.multilingualexamples.data.attachment

import groovy.transform.CompileStatic
import net.ashwork.mc.multilingualexamples.registrar.BlockRegistrar
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import org.quiltmc.qsl.block.content.registry.api.BlockContentRegistries
/**
 * A data provider which generates registry entry attachments for this mod.
 */
@CompileStatic
class ExampleRegistryEntryAttachmentProvider extends RegistryEntryAttachmentProvider {

    /**
     * Default constructor.
     *
     * @param output the output of the data generator
     */
    ExampleRegistryEntryAttachmentProvider(FabricDataOutput output) {
        super(output)
    }

    @Override
    protected void addAttachments() {
        this.attach(BlockContentRegistries.FLATTENABLE)
            .addObject(BlockRegistrar.WAFFLE, BlockRegistrar.SQUISHED_WAFFLE.defaultBlockState())
    }
}
