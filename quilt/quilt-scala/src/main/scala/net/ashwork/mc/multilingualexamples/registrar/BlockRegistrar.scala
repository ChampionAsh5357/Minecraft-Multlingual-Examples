/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.registrar

import com.mojang.datafixers.util.Pair
import net.ashwork.mc.multilingualexamples.MultilingualExamples
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.{BlockItem, CreativeModeTab, Item}
import net.minecraft.world.level.block.{Block, SlabBlock, SoundType}
import net.minecraft.world.level.block.state.{BlockBehaviour, BlockState}
import net.minecraft.world.level.material.{Material, MaterialColor}
import org.jetbrains.annotations.ApiStatus.Internal

import scala.collection.mutable.ListBuffer

/**
 * A utility class used to hold and register all blocks for this mod.
 */
object BlockRegistrar {

    /**
     * A dummy method used to load the static objects in this class.
     */
    def register(): Unit = {}

    private final val BLOCK_ITEM_FACTORIES: ListBuffer[Pair[String, () => Item]] = ListBuffer()

    final val SQUISHED_WAFFLE: SlabBlock = registerBlockWithSimpleItem("squished_waffle",
        SlabBlock(BlockBehaviour.Properties.of(Material.CAKE, MaterialColor.TERRACOTTA_ORANGE)
                .destroyTime(0.2f).sound(SoundType.WOOL)
                .friction(0.7f).speedFactor(0.95f).jumpFactor(0.95f)
                .isValidSpawn((_, _, _, _) => false)
                .isSuffocating((_, _, _) => false)
        ), () => Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(GeneralRegistrar.WAFFLE)
    )
    final val WAFFLE: Block = registerBlockWithSimpleItem("waffle",
        Block(BlockBehaviour.Properties.of(Material.CAKE, MaterialColor.TERRACOTTA_ORANGE)
                .destroyTime(0.2f).sound(SoundType.WOOL)
                .friction(0.7f).speedFactor(0.95f).jumpFactor(0.95f)
                .isValidSpawn((_, _, _, _) => false)
                .isSuffocating((_, _, _) => false)
        ), () => Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(GeneralRegistrar.WAFFLE)
    )

    /**
     * Registers the block items to the item registry.
     *
     * @param itemRegistry a consumer representing the item registry
     */
    @Internal
    def registerBlockItems(itemRegistry: (String, Item) => Unit): Unit = {
        BLOCK_ITEM_FACTORIES.foreach(pair => itemRegistry.apply(pair.getFirst, pair.getSecond.apply))
    }

    /**
     * Registers an object to the block registry with a [[BlockItem]].
     *
     * @param name        the registry name of the object
     * @param obj         the block instance
     * @param itemProperties the properties of the block item
     * @tparam T the type of the block
     * @return the object instance being registered
     */
    private def registerBlockWithSimpleItem[T <: Block](name: String, obj: T, itemProperties: () => Item.Properties): T =
        registerBlockWithItem(name, obj, block => BlockItem(block, itemProperties.apply))

    /**
     * Registers an object to the block registry with an associated item.
     *
     * @param name        the registry name of the object
     * @param obj         the block instance
     * @param itemFactory the factory used to create the item from the block
     * @tparam T the type of the block
     * @tparam I the type of the item
     * @return the object instance being registered
     */
    private def registerBlockWithItem[T <: Block, I <: Item](name: String, obj: T, itemFactory: T => I): T = {
        val block = Registry.register(Registry.BLOCK, ResourceLocation(MultilingualExamples.id(), name), obj)
        BLOCK_ITEM_FACTORIES.addOne(Pair.of(name, () => itemFactory.apply(block)))
        block
    }
}
