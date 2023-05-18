/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data.loot

import groovy.transform.CompileStatic
import net.ashwork.mc.multilingualexamples.registrar.BlockRegistrar
import net.ashwork.mc.multilingualexamples.registrar.Registrars
import net.minecraft.data.loot.BlockLootSubProvider
import net.minecraft.world.flag.FeatureFlags
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
/**
 * A loot table provider for {@link LootContextParamSets#BLOCK}.
 */
@CompileStatic
class ExampleBlockLootSubProvider extends BlockLootSubProvider {

    ExampleBlockLootSubProvider() {
        super(Collections.emptySet() as Set<Item>, FeatureFlags.REGISTRY.allFlags())
    }

    @Override
    protected void generate() {
        this.dropSelf(BlockRegistrar.WAFFLE.get())
        this.add(BlockRegistrar.SQUISHED_WAFFLE.get(), this::createSlabItemTable)
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        /*
        This method is overridden to make sure we are only checking the block
        added to this mod that an associated loot table was generated.
         */
        return Registrars.BLOCKS.entries.collect {it.get() }
    }
}
