/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.registrar

import net.ashwork.mc.multilingualexamples.block.FlattenableBlock
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SlabBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Material
import net.minecraft.world.level.material.MaterialColor
import net.minecraftforge.registries.RegistryObject

/*
 * A utility file used to hold and register all blocks for this mod.
 */

/**
 * A dummy method used to load the static objects in this class.
 */
internal fun registerBlocks() = run { }

val SQUISHED_WAFFLE: RegistryObject<SlabBlock> = registerBlockWithSimpleItem("squished_waffle",
    { SlabBlock(BlockBehaviour.Properties.of(Material.CAKE, MaterialColor.TERRACOTTA_ORANGE)
        .destroyTime(0.2f).sound(SoundType.WOOL)
        .friction(0.7f).speedFactor(0.95f).jumpFactor(0.95f)
        .isValidSpawn { _, _, _, _ -> false }
        .isSuffocating { _, _, _ -> false})
    }) {
    Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(WAFFLE_FOOD)
}
val WAFFLE: RegistryObject<FlattenableBlock> = registerBlockWithSimpleItem("waffle",
    { FlattenableBlock(BlockBehaviour.Properties.of(Material.CAKE, MaterialColor.TERRACOTTA_ORANGE)
        .destroyTime(0.2f).sound(SoundType.WOOL)
        .friction(0.7f).speedFactor(0.95f).jumpFactor(0.95f)
        .isValidSpawn { _, _, _, _ -> false }
        .isSuffocating { _, _, _ -> false}) { SQUISHED_WAFFLE.get() }
    }) {
    Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(WAFFLE_FOOD)
}

/**
 * Registers a block with a [BlockItem].
 *
 * @param name the name of the block
 * @param blockFactory the factory used to create the block
 * @param itemProperties the properties of the block item
 * @return the registered block
 * @param T the type of the block
 */
private inline fun <T: Block> registerBlockWithSimpleItem(name: String, noinline blockFactory: () -> T, crossinline itemProperties: () -> Item.Properties): RegistryObject<T> =
    registerBlockWithItem(name, blockFactory) { BlockItem(it, itemProperties.invoke()) }

/**
 * Registers a block with an associated item.
 *
 * @param name the name of the block
 * @param blockFactory the factory used to create the block
 * @param itemFactory the factory used to create the item from the block
 * @return the registered block
 * @param T the type of the block
 * @param I the type of the item
 */
private inline fun <T: Block, I: Item> registerBlockWithItem(name: String, noinline blockFactory: () -> T, crossinline itemFactory: (T) -> I): RegistryObject<T> =
    BLOCK_REGISTRAR.register(name, blockFactory).also { ITEM_REGISTRAR.register(name) { itemFactory.invoke(it.get()) } }
