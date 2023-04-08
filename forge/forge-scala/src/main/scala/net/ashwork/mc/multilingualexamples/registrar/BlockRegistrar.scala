/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.registrar

import net.ashwork.mc.multilingualexamples.block.FlattenableBlock
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.EntityType
import net.minecraft.world.item.{BlockItem, CreativeModeTab, Item}
import net.minecraft.world.item.Item.Properties
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.state.{BlockBehaviour, BlockState}
import net.minecraft.world.level.block.{Block, SlabBlock, SoundType}
import net.minecraft.world.level.material.{Material, MaterialColor}
import net.minecraftforge.registries.RegistryObject

import scala.jdk.FunctionConverters._

/**
 * A utility object used to hold and register all blocks for this mod.
 */
object BlockRegistrar {

    /**
     * A dummy method used to load the static objects in this class.
     */
    def register(): Unit = {}

    final val SQUISHED_WAFFLE: RegistryObject[SlabBlock] = registerBlockWithSimpleItem("squished_waffle", () =>
        SlabBlock(BlockBehaviour.Properties.of(Material.CAKE, MaterialColor.TERRACOTTA_ORANGE)
            .destroyTime(0.2f).sound(SoundType.WOOL)
            .friction(0.7f).speedFactor(0.95f).jumpFactor(0.95f)
            .isValidSpawn((_, _, _, _) => false)
            .isSuffocating((_, _, _) => false)
        ), () => Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(GeneralRegistrar.WAFFLE)
    )
    final val WAFFLE: RegistryObject[FlattenableBlock] = registerBlockWithSimpleItem("waffle", () =>
        FlattenableBlock(SQUISHED_WAFFLE.asScala, BlockBehaviour.Properties.of(Material.CAKE, MaterialColor.TERRACOTTA_ORANGE)
                .destroyTime(0.2f).sound(SoundType.WOOL)
                .friction(0.7f).speedFactor(0.95f).jumpFactor(0.95f)
                .isValidSpawn((_, _, _, _) => false)
                .isSuffocating((_, _, _) => false)
        ), () => Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(GeneralRegistrar.WAFFLE)
    )
    
    /**
     * Registers a block with a [[BlockItem]].
     *
     * @param name           the name of the block
     * @param blockFactory   the factory used to create the block
     * @param itemProperties the properties of the block item
     * @return the registered block
     * @tparam T the type of the block
     */
    private inline def registerBlockWithSimpleItem[T <: Block](name: String, blockFactory: () => T, itemProperties: () => Properties): RegistryObject[T] =
        registerBlockWithItem(name, blockFactory, BlockItem(_, itemProperties()))
    
    /**
     * Registers a block with an associated item.
     *
     * @param name         the name of the block
     * @param blockFactory the factory used to create the block
     * @param itemFactory  the factory used to create the item from the block
     * @return the registered block
     * @tparam T the type of the block
     * @tparam I the type of the item
     */
    private inline def registerBlockWithItem[T <: Block, I <: Item](name: String, blockFactory: () => T, itemFactory: T => I): RegistryObject[T] = {
        val block = Registrars.BLOCK_REGISTRAR.register(name, blockFactory.asJavaSupplier)
        Registrars.ITEM_REGISTRAR.register(name, () => itemFactory(block.get))
        block
    }
}
