/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.registrar

import net.ashwork.mc.multilingualexamples.ID
import net.fabricmc.fabric.api.registry.FlattenableBlockRegistry
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.item.Item.Properties
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SlabBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Material
import net.minecraft.world.level.material.MaterialColor
import org.jetbrains.annotations.ApiStatus.Internal

/**
 * A dummy method used to load the static objects in this class.
 */
fun registerBlocks() {}

private val blockItemFactories: MutableList<Pair<String, () -> Item>> = mutableListOf()

/**
 * Registers the block items to the item registry.
 *
 * @param itemRegistry a consumer representing the item registry
 */
@Internal
fun registerBlockItems(itemRegistry: (String, Item) -> Unit) {
    blockItemFactories.forEach { itemRegistry(it.first, it.second()) }

    // Invalidate factories after registration
    blockItemFactories.clear()
}

val SQUISHED_WAFFLE: SlabBlock = registerBlockWithSimpleItem("squished_waffle",
    SlabBlock(
        BlockBehaviour.Properties.of(Material.CAKE, MaterialColor.TERRACOTTA_ORANGE)
        .destroyTime(0.2f).sound(SoundType.WOOL)
        .friction(0.7f).speedFactor(0.95f).jumpFactor(0.95f)
        .isValidSpawn { _, _, _, _ -> false }
        .isSuffocating { _, _, _ -> false})
    ) {
    Properties().tab(CreativeModeTab.TAB_FOOD).food(WAFFLE_FOOD)
}
val WAFFLE: Block = registerFlattenableWithSimpleItem("waffle",
    Block(
        BlockBehaviour.Properties.of(Material.CAKE, MaterialColor.TERRACOTTA_ORANGE)
        .destroyTime(0.2f).sound(SoundType.WOOL)
        .friction(0.7f).speedFactor(0.95f).jumpFactor(0.95f)
        .isValidSpawn { _, _, _, _ -> false }
        .isSuffocating { _, _, _ -> false}
    ), SQUISHED_WAFFLE.defaultBlockState()) {
    Properties().tab(CreativeModeTab.TAB_FOOD).food(WAFFLE_FOOD)
}

/**
 * Registers an object to the block registry with a [BlockItem].
 * The block can be flattened with a shovel.
 *
 * @param name the registry name of the object
 * @param obj the block instance
 * @param itemProperties the properties of the block item
 * @param flattened the flattened state of this block
 * @return the object instance being registered
 * @param T the type of the block
 */
private inline fun <T: Block> registerFlattenableWithSimpleItem(name: String, obj: T, flattened: BlockState, crossinline itemProperties: () -> Properties): T =
    registerBlockWithSimpleItem(name, obj, itemProperties).also { FlattenableBlockRegistry.register(it, flattened) }

/**
 * Registers an object to the block registry with a [BlockItem].
 *
 * @param name the registry name of the object
 * @param obj the block instance
 * @param itemProperties the properties of the block item
 * @return the object instance being registered
 * @param T the type of the block
 */
private inline fun <T: Block> registerBlockWithSimpleItem(name: String, obj: T, crossinline itemProperties: () -> Properties): T =
    registerBlockWithItem(name, obj) { BlockItem(it, itemProperties()) }

/**
 * Registers an object to the block registry with an associated item.
 *
 * @param name the registry name of the object
 * @param obj the block instance
 * @param itemFactory the factory used to create the item from the block
 * @return the object instance being registered
 * @param T the type of the block
 * @param I the type of the item
 */
private inline fun <T: Block, I: Item> registerBlockWithItem(name: String, obj: T, crossinline itemFactory: (T) -> I): T =
    Registry.register(Registry.BLOCK, ResourceLocation(ID, name), obj).also { blockItemFactories.add(Pair(name) { itemFactory(it) } ) }
