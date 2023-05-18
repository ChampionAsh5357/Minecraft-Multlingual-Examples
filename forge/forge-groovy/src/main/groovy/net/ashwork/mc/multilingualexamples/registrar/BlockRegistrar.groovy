/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.registrar

import groovy.transform.CompileStatic
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

/**
 * A utility class used to hold and register all blocks for this mod.
 */
@CompileStatic
final class BlockRegistrar {

    /**
     * A dummy method used to load the static objects in this class.
     */
    static void register() {}

    static final RegistryObject<SlabBlock> SQUISHED_WAFFLE = registerBlockWithSimpleItem("squished_waffle",
            { ->
                new SlabBlock(BlockBehaviour.Properties.of(Material.CAKE, MaterialColor.TERRACOTTA_ORANGE)
                    .destroyTime(0.2f).sound(SoundType.WOOL)
                    .friction(0.7f).speedFactor(0.95f).jumpFactor(0.95f)
                    .isValidSpawn({state, getter, pos, type -> false })
                    .isSuffocating({ state, getter, pos  -> false }))
            }) {
        new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(GeneralRegistrar.WAFFLE)
    }
    static final RegistryObject<FlattenableBlock> WAFFLE = registerBlockWithSimpleItem("waffle",
            { ->
                new FlattenableBlock(BlockBehaviour.Properties.of(Material.CAKE, MaterialColor.TERRACOTTA_ORANGE)
                        .destroyTime(0.2f).sound(SoundType.WOOL)
                        .friction(0.7f).speedFactor(0.95f).jumpFactor(0.95f)
                        .isValidSpawn({state, getter, pos, type -> false })
                        .isSuffocating({ state, getter, pos  -> false }),
                        { ->
                            SQUISHED_WAFFLE.get()
                        }
                )
            }) {
        new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(GeneralRegistrar.WAFFLE)
    }

    /**
     * Registers a block with a {@link BlockItem}.
     *
     * @param name the name of the block
     * @param blockFactory the factory used to create the block
     * @param itemProperties the properties of the block item
     * @return the registered block
     * @param <T> the type of the block
     */
    private static <T extends Block> RegistryObject<T> registerBlockWithSimpleItem(String name, Closure<T> blockFactory, Closure<Item.Properties> itemProperties) {
        registerBlockWithItem(name, blockFactory) { T block ->
            new BlockItem(block, itemProperties())
        }
    }

    /**
     * Registers a block with an associated item.
     *
     * @param name the name of the block
     * @param blockFactory the factory used to create the block
     * @param itemFactory the factory used to create the item from the block
     * @return the registered block
     * @param <T> the type of the block
     * @param <I> the type of the item
     */
    private static <T extends Block, I extends Item> RegistryObject<T> registerBlockWithItem(String name, Closure<T> blockFactory, Closure<I> itemFactory) {
        def block = Registrars.BLOCKS.register(name) {
            blockFactory()
        }
        Registrars.ITEMS.register(name) {
            itemFactory(block.get())
        }
        block
    }
}
