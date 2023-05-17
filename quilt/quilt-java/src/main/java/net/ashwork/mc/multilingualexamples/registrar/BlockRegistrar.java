/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.registrar;

import com.mojang.datafixers.util.Pair;
import net.ashwork.mc.multilingualexamples.MultilingualExamples;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A utility class used to hold and register all blocks for this mod.
 */
public class BlockRegistrar {

    /**
     * A dummy method used to load the static objects in this class.
     */
    public static void register() {}

    private static List<Pair<String, Supplier<? extends Item>>> BLOCK_ITEM_FACTORIES = new ArrayList<>();

    /**
     * Registers the block items to the item registry.
     *
     * @param itemRegistry a consumer representing the item registry
     */
    @ApiStatus.Internal
    public static void registerBlockItems(BiConsumer<String, Item> itemRegistry) {
        BLOCK_ITEM_FACTORIES.forEach(pair -> itemRegistry.accept(pair.getFirst(), pair.getSecond().get()));

        // Invalidate factories after registration
        BLOCK_ITEM_FACTORIES.clear();
        BLOCK_ITEM_FACTORIES = null;
    }

    public static final SlabBlock SQUISHED_WAFFLE = registerBlockWithSimpleItem("squished_waffle",
            new SlabBlock(BlockBehaviour.Properties.of(Material.CAKE, MaterialColor.TERRACOTTA_ORANGE)
                    .destroyTime(0.2f).sound(SoundType.WOOL)
                    .friction(0.7f).speedFactor(0.95f).jumpFactor(0.95f)
                    .isValidSpawn((state, getter, pos, type) -> false)
                    .isSuffocating((state, getter, pos) -> false)),
            () -> new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(GeneralRegistrar.WAFFLE.get()));

    public static final Block WAFFLE = registerBlockWithSimpleItem("waffle",
            new Block(BlockBehaviour.Properties.of(Material.CAKE, MaterialColor.TERRACOTTA_ORANGE)
                    .destroyTime(0.2f).sound(SoundType.WOOL)
                    .friction(0.7f).speedFactor(0.95f).jumpFactor(0.95f)
                    .isValidSpawn((state, getter, pos, type) -> false)
                    .isSuffocating((state, getter, pos) -> false)),
            () -> new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(GeneralRegistrar.WAFFLE.get()));

    /**
     * Registers an object to the block registry with a {@link BlockItem}.
     *
     * @param name the registry name of the object
     * @param obj the block instance
     * @param itemProperties the properties of the block item
     * @param <T> the type of the block
     * @return the object instance being registered
     */
    private static <T extends Block> T registerBlockWithSimpleItem(final String name, final T obj, final Supplier<Item.Properties> itemProperties) {
        return registerBlockWithItem(name, obj, block -> new BlockItem(block, itemProperties.get()));
    }

    /**
     * Registers an object to the block registry with an associated item.
     *
     * @param name the registry name of the object
     * @param obj the block instance
     * @param itemFactory the factory used to create the item from the block
     * @param <T> the type of the block
     * @param <I> the type of the item
     * @return the object instance being registered
     */
    private static <T extends Block, I extends Item> T registerBlockWithItem(final String name, final T obj, final Function<T, I> itemFactory) {
        var block = Registry.register(Registry.BLOCK, new ResourceLocation(MultilingualExamples.id(), name), obj);
        BLOCK_ITEM_FACTORIES.add(Pair.of(name, () -> itemFactory.apply(block)));
        return block;
    }
}
