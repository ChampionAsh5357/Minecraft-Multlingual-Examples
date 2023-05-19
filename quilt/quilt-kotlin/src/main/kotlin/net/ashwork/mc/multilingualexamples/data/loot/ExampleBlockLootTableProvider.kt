/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data.loot

import net.ashwork.mc.multilingualexamples.registrar.SQUISHED_WAFFLE
import net.ashwork.mc.multilingualexamples.registrar.WAFFLE
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
import java.util.function.BiConsumer

/**
 * A loot table provider for [LootContextParamSets.BLOCK].
 *
 * @param output the output of the data generator
 */
class ExampleBlockLootTableProvider(output: FabricDataOutput): FabricBlockLootTableProvider(output) {

    override fun generate() {
        this.dropSelf(WAFFLE)
        this.add(SQUISHED_WAFFLE, ::createSlabItemTable)
    }
    /*
    Fabric has a weird bug that requires this implementation. Essentially,
    #accept is #generate in Yarn mappings. The provider implemented a
    consumer, meaning that #accept in Yarn mappings was properly overridden.
    However, in MojMaps, this is not the case and the method needs to be
    manually delegated.

    This would probably never come about during testing in a normal situation.
    This method would need to be removed when in Yarn mappings as otherwise it
    would be a recursive call.
     */
    override fun accept(writer: BiConsumer<ResourceLocation, LootTable.Builder>) = this.generate(writer)
}
