/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data

import net.ashwork.mc.multilingualexamples.registrar.ItemRegistrar
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.models.model.ModelTemplates
import net.minecraft.data.models.{BlockModelGenerators, ItemModelGenerators}

/**
 * A data provider which generates models for this mod.
 *
 * @param gen the generator being written to
 */
class Models(gen: FabricDataGenerator) extends FabricModelProvider(gen) {

    override def generateBlockStateModels(generators: BlockModelGenerators): Unit = {}

    override def generateItemModels(generators: ItemModelGenerators): Unit = {
        // Simple item models through the 'item/generated' parent.
        generators.generateFlatItem(ItemRegistrar.ASH, ModelTemplates.FLAT_ITEM)
        generators.generateFlatItem(ItemRegistrar.COLLAGE_HELMET, ModelTemplates.FLAT_ITEM)
        generators.generateFlatItem(ItemRegistrar.COLLAGE_CHESTPLATE, ModelTemplates.FLAT_ITEM)
        generators.generateFlatItem(ItemRegistrar.COLLAGE_LEGGINGS, ModelTemplates.FLAT_ITEM)
        generators.generateFlatItem(ItemRegistrar.COLLAGE_BOOTS, ModelTemplates.FLAT_ITEM)
    }
}
