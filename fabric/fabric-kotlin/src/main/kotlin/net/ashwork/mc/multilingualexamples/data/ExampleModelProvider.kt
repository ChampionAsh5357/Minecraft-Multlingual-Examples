/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data

import net.ashwork.mc.multilingualexamples.registrar.*
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.data.models.ItemModelGenerators
import net.minecraft.data.models.model.ModelTemplates
import net.minecraft.data.models.model.TextureMapping
import net.minecraft.data.models.model.TextureSlot
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block

/**
 * A data provider which generates models for this mod.
 *
 * @param gen the generator being written to
 */
class ExampleModelProvider(gen: FabricDataGenerator): FabricModelProvider(gen) {

    override fun generateBlockStateModels(generators: BlockModelGenerators) {
        /*
        Item models are generated automatically for blocks which have an item delegate.
         */

        // Simple block models through 'block/cube_all' parent
        generators.createTrivialCube(WAFFLE)

        // Custom model handlers
        createSlab(SQUISHED_WAFFLE, TextureMapping.getBlockTexture(SQUISHED_WAFFLE), TextureMapping.getBlockTexture(WAFFLE), generators)
    }

    override fun generateItemModels(generators: ItemModelGenerators) {
        // Simple item models through the 'item/generated' parent.
        generators.generateFlatItem(ASH, ModelTemplates.FLAT_ITEM)
        generators.generateFlatItem(COLLAGE_HELMET, ModelTemplates.FLAT_ITEM)
        generators.generateFlatItem(COLLAGE_CHESTPLATE, ModelTemplates.FLAT_ITEM)
        generators.generateFlatItem(COLLAGE_LEGGINGS, ModelTemplates.FLAT_ITEM)
        generators.generateFlatItem(COLLAGE_BOOTS, ModelTemplates.FLAT_ITEM)
        generators.generateFlatItem(WAFFLE_MIX, ModelTemplates.FLAT_ITEM)
        generators.generateFlatItem(WAFFLE_CONE, ModelTemplates.FLAT_ITEM)
        generators.generateFlatItem(SNOW_CONE, ModelTemplates.FLAT_ITEM)
        generators.generateFlatItem(ICE_CREAM_SANDWICH, ModelTemplates.FLAT_ITEM)
    }

    /**
     * Creates a slab model using the half slab and full block textures.
     *
     * @param block the block to generate the model for
     * @param slab the location of the slab texture
     * @param full the location of the full block texture
     * @param generators the generator for block models
     */
    private fun createSlab(block: Block, slab: ResourceLocation, full: ResourceLocation, generators: BlockModelGenerators) {
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
