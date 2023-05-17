/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data

import net.ashwork.mc.multilingualexamples.registrar.*
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.data.models.ItemModelGenerators
import net.minecraft.data.models.model.ModelTemplates

/**
 * A data provider which generates models for this mod.
 *
 * @param gen the generator being written to
 */
class Models(gen: FabricDataGenerator): FabricModelProvider(gen) {

    override fun generateBlockStateModels(generators: BlockModelGenerators) {}

    override fun generateItemModels(generators: ItemModelGenerators) {
        // Simple item models through the 'item/generated' parent.
        generators.generateFlatItem(ASH, ModelTemplates.FLAT_ITEM)
        generators.generateFlatItem(COLLAGE_HELMET, ModelTemplates.FLAT_ITEM)
        generators.generateFlatItem(COLLAGE_CHESTPLATE, ModelTemplates.FLAT_ITEM)
        generators.generateFlatItem(COLLAGE_LEGGINGS, ModelTemplates.FLAT_ITEM)
        generators.generateFlatItem(COLLAGE_BOOTS, ModelTemplates.FLAT_ITEM)
    }
}
