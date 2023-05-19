/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data

import net.ashwork.mc.multilingualexamples.registrar.{BlockRegistrar, ItemRegistrar}
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.models.model.{ModelTemplates, TextureMapping, TextureSlot}
import net.minecraft.data.models.{BlockModelGenerators, ItemModelGenerators}
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block

/**
 * A data provider which generates models for this mod.
 *
 * @param output the output of the data generator
 */
class ExampleModelProvider(output: FabricDataOutput) extends FabricModelProvider(output) {

    override def generateBlockStateModels(generators: BlockModelGenerators): Unit = {
        /*
        Item models are generated automatically for blocks which have an item delegate.
         */

        // Simple block models through 'block/cube_all' parent
        generators.createTrivialCube(BlockRegistrar.WAFFLE)

        // Custom model handlers
        createSlab(BlockRegistrar.SQUISHED_WAFFLE, TextureMapping.getBlockTexture(BlockRegistrar.SQUISHED_WAFFLE), TextureMapping.getBlockTexture(BlockRegistrar.WAFFLE), generators)
    }

    override def generateItemModels(generators: ItemModelGenerators): Unit = {
        // Simple item models through the 'item/generated' parent.
        generators.generateFlatItem(ItemRegistrar.ASH, ModelTemplates.FLAT_ITEM)
        generators.generateFlatItem(ItemRegistrar.COLLAGE_HELMET, ModelTemplates.FLAT_ITEM)
        generators.generateFlatItem(ItemRegistrar.COLLAGE_CHESTPLATE, ModelTemplates.FLAT_ITEM)
        generators.generateFlatItem(ItemRegistrar.COLLAGE_LEGGINGS, ModelTemplates.FLAT_ITEM)
        generators.generateFlatItem(ItemRegistrar.COLLAGE_BOOTS, ModelTemplates.FLAT_ITEM)
        generators.generateFlatItem(ItemRegistrar.WAFFLE_MIX, ModelTemplates.FLAT_ITEM)
        generators.generateFlatItem(ItemRegistrar.WAFFLE_CONE, ModelTemplates.FLAT_ITEM)
        generators.generateFlatItem(ItemRegistrar.SNOW_CONE, ModelTemplates.FLAT_ITEM)
        generators.generateFlatItem(ItemRegistrar.ICE_CREAM_SANDWICH, ModelTemplates.FLAT_ITEM)
    }

    /**
     * Creates a slab model using the half slab and full block textures.
     *
     * @param block      the block to generate the model for
     * @param slab       the location of the slab texture
     * @param full       the location of the full block texture
     * @param generators the generator for block models
     */
    private def createSlab(block: Block, slab: ResourceLocation, full: ResourceLocation, generators: BlockModelGenerators): Unit = {
        val texture = TextureMapping().put(TextureSlot.BOTTOM, full).put(TextureSlot.TOP, full).put(TextureSlot.SIDE, slab)
        val bottomSlab = ModelTemplates.SLAB_BOTTOM.create(block, texture, generators.modelOutput)
        generators.blockStateOutput.accept(
            BlockModelGenerators.createSlab(block, bottomSlab,
                ModelTemplates.SLAB_TOP.create(block, texture, generators.modelOutput),
                ModelTemplates.CUBE_BOTTOM_TOP.createWithOverride(block, "_double", texture, generators.modelOutput)
            )
        )
        generators.delegateItemModel(block, bottomSlab)
    }
}
