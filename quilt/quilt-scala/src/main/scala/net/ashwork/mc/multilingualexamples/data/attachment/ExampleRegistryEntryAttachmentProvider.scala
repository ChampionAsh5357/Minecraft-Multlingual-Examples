/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data.attachment

import net.ashwork.mc.multilingualexamples.registrar.BlockRegistrar
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import org.quiltmc.qsl.block.content.registry.api.BlockContentRegistries

/**
 * A data provider which generates registry entry attachments for this mod.
 *
 * @param output the output of the data generator
 */
class ExampleRegistryEntryAttachmentProvider(output: FabricDataOutput) extends RegistryEntryAttachmentProvider(output) {

    override def addAttachments(): Unit = {
        this.attach(BlockContentRegistries.FLATTENABLE)
                .addObject(BlockRegistrar.WAFFLE, BlockRegistrar.SQUISHED_WAFFLE.defaultBlockState)
    }
}
