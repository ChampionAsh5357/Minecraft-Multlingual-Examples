/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data;

import net.ashwork.mc.multilingualexamples.registrar.ItemRegistrar;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;

/**
 * A data provider which generates models for this mod.
 */
public final class Models extends FabricModelProvider {

    /**
     * A simple constructor.
     *
     * @param gen the generator being written to
     */
    public Models(FabricDataGenerator gen) {
        super(gen);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators generators) {}

    @Override
    public void generateItemModels(ItemModelGenerators generators) {
        // Simple item models through the 'item/generated' parent.
        generators.generateFlatItem(ItemRegistrar.ASH, ModelTemplates.FLAT_ITEM);
    }
}
