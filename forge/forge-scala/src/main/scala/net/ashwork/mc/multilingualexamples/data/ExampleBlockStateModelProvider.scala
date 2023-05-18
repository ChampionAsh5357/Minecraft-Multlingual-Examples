/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data

import net.ashwork.mc.multilingualexamples.MultilingualExamples
import net.ashwork.mc.multilingualexamples.registrar.BlockRegistrar
import net.minecraft.data.PackOutput
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraftforge.client.model.generators.{BlockStateProvider, ModelFile, ModelProvider}
import net.minecraftforge.common.data.ExistingFileHelper
import net.minecraftforge.registries.RegistryObject

/**
 * A data provider which generates block state, block, and item models for blocks
 * in this mod.
 *
 * @param output the output of the data generator
 * @param efh a resource holder for linking existing files
 */
class ExampleBlockStateModelProvider(output: PackOutput, efh: ExistingFileHelper) extends BlockStateProvider(output, MultilingualExamples.ID, efh) {

    override def registerStatesAndModels(): Unit = {
        this.cubeAllItem(BlockRegistrar.WAFFLE)
        this.blockWithItem(BlockRegistrar.SQUISHED_WAFFLE, (name, block) => {
            val waffle = BlockRegistrar.WAFFLE.getId.withPrefix(s"${ModelProvider.BLOCK_FOLDER}/")
            val squishedWaffle = name.withPrefix(s"${ModelProvider.BLOCK_FOLDER}/")
            val regularSlab = models().slab(name.toString, squishedWaffle, waffle, waffle)
            slabBlock(block, regularSlab, models().slabTop(s"${name}_top", squishedWaffle, waffle, waffle), models().cubeBottomTop(s"${name}_double", squishedWaffle, waffle, waffle))
            regularSlab
        })
    }

    /**
     * Generates a simple, single-state block model whose item model is the same
     * block model.
     *
     * @param block the block whose model is being generated
     */
    private inline def cubeAllItem(block: RegistryObject[_ <: Block]): Unit =
        blockWithItem(block, (_, b) => {
            val model = cubeAll(b)
            simpleBlock(b, model)
            model
        })

    /**
     * Generates an item model for the specified block along with the applied
     * block models.
     *
     * @param block       the block whose model is being generated
     * @param blockModels a function which returns the block model the item will parent
     * @tparam T the type of the block
     */
    private inline def blockWithItem[T <: Block](block: RegistryObject[T], blockModels: (ResourceLocation, T) => ModelFile): Unit =
        block.map(b => simpleBlockItem(b, blockModels(block.getId, b)))
}
