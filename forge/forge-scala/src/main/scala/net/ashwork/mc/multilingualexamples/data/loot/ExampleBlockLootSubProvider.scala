/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data.loot

import net.ashwork.mc.multilingualexamples.registrar.{BlockRegistrar, Registrars}
import net.minecraft.data.loot.BlockLootSubProvider
import net.minecraft.world.flag.FeatureFlags
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets

import java.lang
import scala.jdk.CollectionConverters.*


/**
 * A loot table provider for [[LootContextParamSets.BLOCK]].
 */
class ExampleBlockLootSubProvider extends BlockLootSubProvider(Set.empty[Item].asJava, FeatureFlags.REGISTRY.allFlags) {

    override def generate(): Unit = {
        this.dropSelf(BlockRegistrar.WAFFLE.get)
        this.add(BlockRegistrar.SQUISHED_WAFFLE.get, this.createSlabItemTable _)
    }

    /*
    This method is overridden to make sure we are only checking the block
    added to this mod that an associated loot table was generated.
     */
    override def getKnownBlocks: lang.Iterable[Block] =
        Registrars.BLOCK_REGISTRAR.getEntries.asScala.map(_.get).asJava
}
