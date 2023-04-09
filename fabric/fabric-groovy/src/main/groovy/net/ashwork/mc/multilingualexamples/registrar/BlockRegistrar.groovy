package net.ashwork.mc.multilingualexamples.registrar

import com.mojang.datafixers.util.Pair
import groovy.transform.CompileStatic
import net.ashwork.mc.multilingualexamples.MultilingualExamples
import net.fabricmc.fabric.api.registry.FlattenableBlockRegistry
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SlabBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Material
import net.minecraft.world.level.material.MaterialColor
import org.jetbrains.annotations.ApiStatus
/**
 * A utility class used to hold and register all blocks for this mod.
 */
@CompileStatic
class BlockRegistrar {

    /**
     * A dummy method used to load the static objects in this class.
     */
    static void registerBlocks() {}

    private static List<Pair<String, Closure<? extends Item>>> BLOCK_ITEM_FACTORIES = []

    /**
     * Registers the block items to the item registry.
     *
     * @param itemRegistry a consumer representing the item registry
     */
    @ApiStatus.Internal
    static void registerBlockItems(Closure<?> itemRegistry) {
        BLOCK_ITEM_FACTORIES.forEach { itemRegistry(it.getFirst(), it.getSecond()()) }

        // Invalidate factories after registration
        BLOCK_ITEM_FACTORIES.clear()
        BLOCK_ITEM_FACTORIES = null
    }

    static final SlabBlock SQUISHED_WAFFLE = registerBlockWithSimpleItem('squished_waffle',
            new SlabBlock(BlockBehaviour.Properties.of(Material.CAKE, MaterialColor.TERRACOTTA_ORANGE)
                    .destroyTime(0.2f).sound(SoundType.WOOL)
                    .friction(0.7f).speedFactor(0.95f).jumpFactor(0.95f)
                    .isValidSpawn {state, getter, pos, type -> false }
                    .isSuffocating {state, getter, pos -> false })) {
        new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(GeneralRegistrar.WAFFLE)
    }

    static final Block WAFFLE = registerFlattenableWithSimpleItem('waffle',
            new Block(BlockBehaviour.Properties.of(Material.CAKE, MaterialColor.TERRACOTTA_ORANGE)
                    .destroyTime(0.2f).sound(SoundType.WOOL)
                    .friction(0.7f).speedFactor(0.95f).jumpFactor(0.95f)
                    .isValidSpawn {state, getter, pos, type -> false }
                    .isSuffocating {state, getter, pos -> false }
            ), SQUISHED_WAFFLE.defaultBlockState()) {
        new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(GeneralRegistrar.WAFFLE)
    }

    /**
     * Registers an object to the block registry with a {@link BlockItem}.
     * The block can be flattened with a shovel.
     *
     * @param name the registry name of the object
     * @param obj the block instance
     * @param itemProperties the properties of the block item
     * @param flattened the flattened state of this block
     * @param <T> the type of the block
     * @return the object instance being registered
     */
    private static <T extends Block> T registerFlattenableWithSimpleItem(final String name, final T obj, final BlockState flattened, final Closure<Item.Properties> itemProperties) {
        var block = registerBlockWithSimpleItem(name, obj, itemProperties)
        FlattenableBlockRegistry.register(block, flattened)
        return block
    }

    /**
     * Registers an object to the block registry with a {@link BlockItem}.
     *
     * @param name the registry name of the object
     * @param obj the block instance
     * @param itemProperties the properties of the block item
     * @param <T> the type of the block
     * @return the object instance being registered
     */
    private static <T extends Block> T registerBlockWithSimpleItem(final String name, final T obj, final Closure<Item.Properties> itemProperties) {
        return registerBlockWithItem(name, obj) { T block ->
            new BlockItem(block, itemProperties())
        }
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
    private static <T extends Block, I extends Item> T registerBlockWithItem(final String name, final T obj, final Closure<I> itemFactory) {
        var block = Registry.register(Registry.BLOCK, new ResourceLocation(MultilingualExamples.ID, name), obj)
        BLOCK_ITEM_FACTORIES.add(Pair.of(name) { itemFactory(block) })
        return block
    }
}
