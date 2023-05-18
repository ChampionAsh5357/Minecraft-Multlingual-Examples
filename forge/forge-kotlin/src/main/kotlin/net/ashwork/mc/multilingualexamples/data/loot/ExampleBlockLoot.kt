/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data.loot

import net.ashwork.mc.multilingualexamples.registrar.BLOCK_REGISTRAR
import net.ashwork.mc.multilingualexamples.registrar.SQUISHED_WAFFLE
import net.ashwork.mc.multilingualexamples.registrar.WAFFLE
import net.minecraft.data.loot.BlockLoot
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets

/**
 * A loot table provider for [LootContextParamSets.BLOCK].
 */
class ExampleBlockLoot: BlockLoot() {

    override fun addTables() {
        this.dropSelf(WAFFLE.get())
        this.add(SQUISHED_WAFFLE.get(), BlockLoot::createSlabItemTable)
    }

    /*
    This method is overridden to make sure we are only checking the block
    added to this mod that an associated loot table was generated.
     */
    override fun getKnownBlocks(): MutableIterable<Block> =
        BLOCK_REGISTRAR.entries.map { it.get() }.toMutableList()
}
