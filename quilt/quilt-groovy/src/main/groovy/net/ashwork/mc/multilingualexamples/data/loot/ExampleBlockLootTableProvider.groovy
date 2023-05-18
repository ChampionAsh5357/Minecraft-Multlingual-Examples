/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data.loot

import groovy.transform.CompileStatic
import net.ashwork.mc.multilingualexamples.registrar.BlockRegistrar
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.data.loot.BlockLoot
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets

/**
 * A loot table provider for {@link LootContextParamSets#BLOCK}.
 */
@CompileStatic
class ExampleBlockLootTableProvider extends FabricBlockLootTableProvider {

    /**
     * A simple constructor.
     *
     * @param gen the generator being written to
     */
    ExampleBlockLootTableProvider(FabricDataGenerator gen) {
        super(gen)
    }

    @Override
    protected void generateBlockLootTables() {
        this.dropSelf(BlockRegistrar.WAFFLE)
        this.add(BlockRegistrar.SQUISHED_WAFFLE, BlockLoot::createSlabItemTable)
    }
}
