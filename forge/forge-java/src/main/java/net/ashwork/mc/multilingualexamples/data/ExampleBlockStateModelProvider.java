/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data;

import net.ashwork.mc.multilingualexamples.MultilingualExamples;
import net.ashwork.mc.multilingualexamples.registrar.BlockRegistrar;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

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
    }

    /**
     * Generates a simple, single-state block model whose item model is the same
     * block model.
     *
     * @param block the block whose model is being generated
     */
    private void cubeAllItem(Supplier<? extends Block> block) {
        var b = block.get();
        var bModel = this.cubeAll(b);
        this.simpleBlock(b, bModel);
        this.simpleBlockItem(b, bModel);
    }
}
