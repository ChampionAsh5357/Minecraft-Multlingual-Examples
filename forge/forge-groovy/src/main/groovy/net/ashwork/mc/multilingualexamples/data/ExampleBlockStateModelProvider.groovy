/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data

import groovy.transform.CompileStatic
import net.ashwork.mc.multilingualexamples.MultilingualExamples
import net.ashwork.mc.multilingualexamples.registrar.BlockRegistrar
import net.minecraft.data.PackOutput
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SlabBlock
import net.minecraftforge.client.model.generators.BlockStateProvider
import net.minecraftforge.client.model.generators.ModelFile
import net.minecraftforge.client.model.generators.ModelProvider
import net.minecraftforge.common.data.ExistingFileHelper
import net.minecraftforge.registries.RegistryObject
/**
 * A data provider which generates block state, block, and item models for blocks
 * in this mod.
 */
@CompileStatic
class ExampleBlockStateModelProvider extends BlockStateProvider {

    /**
     * Default constructor.
     *
     * @param output the output of the data generator
     * @param efh a resource holder for linking existing files
     */
    ExampleBlockStateModelProvider(PackOutput output, ExistingFileHelper efh) {
        super(output, MultilingualExamples.ID, efh)
    }

    @Override
    protected void registerStatesAndModels() {
        this.cubeAllItem(BlockRegistrar.WAFFLE)
        this.blockWithItem(BlockRegistrar.SQUISHED_WAFFLE) { ResourceLocation name, SlabBlock block ->
            ResourceLocation waffle = BlockRegistrar.WAFFLE.id.withPrefix("${ModelProvider.BLOCK_FOLDER}/")
            ResourceLocation squishedWaffle = name.withPrefix("${ModelProvider.BLOCK_FOLDER}/")
            def regularSlab = this.models().slab(name.toString(), squishedWaffle, waffle, waffle)
            this.slabBlock(block, regularSlab, this.models().slabTop("${name}_top", squishedWaffle, waffle, waffle), this.models().cubeBottomTop("${name}_double", squishedWaffle, waffle, waffle))
            return regularSlab
        }
    }

    /**
     * Generates a simple, single-state block model whose item model is the same
     * block model.
     *
     * @param block the block whose model is being generated
     */
    private void cubeAllItem(RegistryObject<? extends Block> block) {
        blockWithItem(block) { ResourceLocation name, Block b ->
            def model = this.cubeAll(b)
            this.simpleBlock(b, model)
            return model
        }
    }

    /**
     * Generates an item model for the specified block along with the applied
     * block models.
     *
     * @param block the block whose model is being generated
     * @param blockModels a function which returns the block model the item will parent
     * @param <T> the type of the block
     */
    private <T extends Block> void blockWithItem(RegistryObject<T> block, Closure<ModelFile> blockModels) {
        block.map {
            this.simpleBlockItem(it, blockModels(block.id, it))
        }
    }
}
