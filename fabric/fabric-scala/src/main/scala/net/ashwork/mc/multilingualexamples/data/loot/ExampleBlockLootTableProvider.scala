/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data.loot

import net.ashwork.mc.multilingualexamples.registrar.BlockRegistrar
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.data.loot.BlockLoot

/**
 * A loot table provider for [[LootContextParamSets.BLOCK]].
 *
 * @param gen the generator being written to
 */
class ExampleBlockLootTableProvider(gen: FabricDataGenerator) extends FabricBlockLootTableProvider(gen) {

    override def generateBlockLootTables(): Unit = {
        this.dropSelf(BlockRegistrar.WAFFLE)
        this.add(BlockRegistrar.SQUISHED_WAFFLE, BlockLoot.createSlabItemTable _)
    }
}
