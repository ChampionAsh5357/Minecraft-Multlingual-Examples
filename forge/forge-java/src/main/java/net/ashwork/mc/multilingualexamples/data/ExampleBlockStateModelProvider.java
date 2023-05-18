/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data;

import net.ashwork.mc.multilingualexamples.MultilingualExamples;
import net.ashwork.mc.multilingualexamples.registrar.BlockRegistrar;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.BiFunction;

/**
 * A data provider which generates block state, block, and item models for blocks
 * in this mod.
 */
public final class ExampleBlockStateModelProvider extends BlockStateProvider implements ModelProviderExtension {

    /**
     * Default constructor.
     *
     * @param gen the generator being written to
     * @param efh a resource holder for linking existing files
     */
    public ExampleBlockStateModelProvider(final DataGenerator gen, final ExistingFileHelper efh) {
        super(gen, MultilingualExamples.ID, efh);
    }

    @Override
    protected void registerStatesAndModels() {
        this.cubeAllItem(BlockRegistrar.WAFFLE);
        this.blockWithItem(BlockRegistrar.SQUISHED_WAFFLE, (name, block) -> {
            var waffle = this.prefix(BlockRegistrar.WAFFLE.getId(), ModelProvider.BLOCK_FOLDER);
            var squishedWaffle = this.prefix(name, ModelProvider.BLOCK_FOLDER);
            var regularSlab = this.models().slab(name.toString(), squishedWaffle, waffle, waffle);
            this.slabBlock(block, regularSlab, this.models().slabTop(name + "_top", squishedWaffle, waffle, waffle), this.models().cubeBottomTop(name + "_double", squishedWaffle, waffle, waffle));
            return regularSlab;
        });
    }

    /**
     * Generates a simple, single-state block model whose item model is the same
     * block model.
     *
     * @param block the block whose model is being generated
     */
    private void cubeAllItem(RegistryObject<? extends Block> block) {
        blockWithItem(block, (name, b) -> {
            var model = this.cubeAll(b);
            this.simpleBlock(b, model);
            return model;
        });
    }

    /**
     * Generates an item model for the specified block along with the applied
     * block models.
     *
     * @param block the block whose model is being generated
     * @param blockModels a function which returns the block model the item will parent
     * @param <T> the type of the block
     */
    private <T extends Block> void blockWithItem(RegistryObject<T> block, BiFunction<ResourceLocation, T, ModelFile> blockModels) {
        var b = block.get();
        this.simpleBlockItem(b, blockModels.apply(block.getId(), b));
    }
}
