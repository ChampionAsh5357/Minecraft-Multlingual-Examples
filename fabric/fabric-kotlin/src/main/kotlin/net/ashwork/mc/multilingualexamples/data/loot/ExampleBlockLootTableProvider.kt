package net.ashwork.mc.multilingualexamples.data.loot

import net.ashwork.mc.multilingualexamples.registrar.SQUISHED_WAFFLE
import net.ashwork.mc.multilingualexamples.registrar.WAFFLE
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.data.loot.BlockLoot
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets

/**
 * A loot table provider for [LootContextParamSets.BLOCK].
 *
 * @param gen the generator being written to
 */
class ExampleBlockLootTableProvider(gen: FabricDataGenerator): FabricBlockLootTableProvider(gen) {

    override fun generateBlockLootTables() {
        this.dropSelf(WAFFLE)
        this.add(SQUISHED_WAFFLE, BlockLoot::createSlabItemTable)
    }
}
