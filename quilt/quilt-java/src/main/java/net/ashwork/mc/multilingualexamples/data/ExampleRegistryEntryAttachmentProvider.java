/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data;

import net.ashwork.mc.multilingualexamples.registrar.BlockRegistrar;
import net.minecraft.data.DataGenerator;
import org.quiltmc.qsl.block.content.registry.api.BlockContentRegistries;

/**
 * A data provider which generates registry entry attachments for this mod.
 */
public class ExampleRegistryEntryAttachmentProvider extends RegistryEntryAttachmentProvider {

    /**
     * A simple constructor.
     *
     * @param gen the generator being written to
     */
    public ExampleRegistryEntryAttachmentProvider(DataGenerator gen) {
        super(gen);
    }

    @Override
    protected void addAttachments() {
        this.attach(BlockContentRegistries.FLATTENABLE_BLOCK)
                .addObject(BlockRegistrar.WAFFLE, BlockRegistrar.SQUISHED_WAFFLE.defaultBlockState());
    }
}
