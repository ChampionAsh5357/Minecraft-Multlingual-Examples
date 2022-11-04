package net.ashwork.mc.multilingualexamples.registrar;

import net.ashwork.mc.multilingualexamples.block.FlattenableBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.RegistryObject;

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

    public static final RegistryObject<SlabBlock> SQUISHED_WAFFLE = registerBlockWithSimpleItem("squished_waffle",
            () -> new SlabBlock(BlockBehaviour.Properties.of(Material.CAKE, MaterialColor.TERRACOTTA_ORANGE)
                    .destroyTime(0.2f).sound(SoundType.WOOL)
                    .friction(0.7f).speedFactor(0.95f).jumpFactor(0.95f)
                    .isValidSpawn(((state, getter, pos, type) -> false))
                    .isSuffocating((state, getter, pos) -> false)),
            () -> new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(GeneralRegistrar.WAFFLE.get()));

    public static final RegistryObject<FlattenableBlock> WAFFLE = registerBlockWithSimpleItem("waffle",
            () -> new FlattenableBlock(SQUISHED_WAFFLE, BlockBehaviour.Properties.of(Material.CAKE, MaterialColor.TERRACOTTA_ORANGE)
                    .destroyTime(0.2f).sound(SoundType.WOOL)
                    .friction(0.7f).speedFactor(0.95f).jumpFactor(0.95f)
                    .isValidSpawn(((state, getter, pos, type) -> false))
                    .isSuffocating((state, getter, pos) -> false)),
            () -> new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(GeneralRegistrar.WAFFLE.get()));

    /**
     * Registers a block with a {@link BlockItem}.
     *
     * @param name the name of the block
     * @param blockFactory the factory used to create the block
     * @param itemProperties the properties of the block item
     * @return the registered block
     * @param <T> the type of the block
     */
    private static <T extends Block> RegistryObject<T> registerBlockWithSimpleItem(String name, Supplier<T> blockFactory, Supplier<Item.Properties> itemProperties) {
        return registerBlockWithItem(name, blockFactory, block -> new BlockItem(block, itemProperties.get()));
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
    private static <T extends Block, I extends Item> RegistryObject<T> registerBlockWithItem(String name, Supplier<T> blockFactory, Function<T, I> itemFactory) {
        var block = Registrars.BLOCKS.register(name, blockFactory);
        Registrars.ITEMS.register(name, () -> itemFactory.apply(block.get()));
        return block;
    }
}
