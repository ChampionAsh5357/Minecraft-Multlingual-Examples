/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data

import net.ashwork.mc.multilingualexamples.ID
import net.ashwork.mc.multilingualexamples.registrar.SQUISHED_WAFFLE
import net.ashwork.mc.multilingualexamples.registrar.WAFFLE
import net.ashwork.mc.multilingualexamples.util.prefix
import net.minecraft.data.DataGenerator
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraftforge.client.model.generators.BlockStateProvider
import net.minecraftforge.client.model.generators.ModelFile
import net.minecraftforge.client.model.generators.ModelProvider
import net.minecraftforge.common.data.ExistingFileHelper
import net.minecraftforge.registries.RegistryObject

/**
 * A data provider which generates block state, block, and item models for blocks
 * in this mod.
 *
 * @param gen the generator being written to
 * @param efh a resource holder for linking existing files
 */
class ExampleBlockStateModelProvider(gen: DataGenerator, efh: ExistingFileHelper): BlockStateProvider(gen, ID, efh) {

    override fun registerStatesAndModels() {
        this.cubeAllItem(WAFFLE)
        this.blockWithItem(SQUISHED_WAFFLE) { name, block ->
            val waffle = WAFFLE.id prefix ModelProvider.BLOCK_FOLDER
            val squishedWaffle = name prefix ModelProvider.BLOCK_FOLDER
            this.models().slab(name.toString(), squishedWaffle, waffle, waffle).also {
                this.slabBlock(block, it, this.models().slabTop("${name}_top", squishedWaffle, waffle, waffle), this.models().cubeBottomTop("${name}_double", squishedWaffle, waffle, waffle))
            }
        }
    }

    /**
     * Generates a simple, single-state block model whose item model is the same
     * block model.
     *
     * @param block the block whose model is being generated
     */
    private fun cubeAllItem(block: RegistryObject<out Block>) =
        blockWithItem(block) { _, b ->
            this.cubeAll(b).also { this.simpleBlock(b, it) }
        }

    /**
     * Generates an item model for the specified block along with the applied
     * block models.
     *
     * @param block the block whose model is being generated
     * @param blockModels a function which returns the block model the item will parent
     * @param T the type of the block
     */
    private inline fun <T: Block> blockWithItem(block: RegistryObject<T>, crossinline blockModels: (ResourceLocation, T) -> ModelFile) =
        block.map { this.simpleBlockItem(it, blockModels.invoke(block.id, it)) }
}
