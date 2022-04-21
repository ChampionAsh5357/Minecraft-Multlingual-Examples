/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data

import net.ashwork.mc.multilingualexamples.ID
import net.ashwork.mc.multilingualexamples.util.prefix
import net.ashwork.mc.multilingualexamples.registrar.ASH
import net.ashwork.mc.multilingualexamples.util.kt
import net.minecraft.data.DataGenerator
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraftforge.client.model.generators.ItemModelProvider
import net.minecraftforge.common.data.ExistingFileHelper

/**
 * A data provider which generates item models for the mod.
 *
 * @param gen the generator being written to
 * @param efh a resource holder for linking existing files
 */
internal class ItemModels(gen: DataGenerator, efh: ExistingFileHelper) : ItemModelProvider(gen, ID, efh) {

    override fun registerModels() {
        this.simpleItem(ASH.kt)
    }

    /**
     * Creates a simple item model through the 'item/generated' parent.
     *
     * @param item the item whose model is being generated
     */
    private fun simpleItem(item: () -> Item) = this.simpleItem(item, item.invoke().registryName!!)

    /**
     * Creates a simple item model through the 'item/generated' parent with a single
     * layer.
     *
     * [layer0] should point to a generalized resource location. The item subdirectory
     * transformation is done within this method.
     *
     * @param item the item whose model is being generated
     * @param layer0 the name of the layer0 texture living in 'textures/item'
     */
    private fun simpleItem(item: () -> Item, layer0: ResourceLocation) =
        this.withExistingParent(item.invoke().registryName!!.toString(), "item/generated")
            .texture("layer0", layer0 prefix this.folder)
}