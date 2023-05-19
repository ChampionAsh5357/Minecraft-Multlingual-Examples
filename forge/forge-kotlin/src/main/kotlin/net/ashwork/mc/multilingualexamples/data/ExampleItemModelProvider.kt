/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data

import net.ashwork.mc.multilingualexamples.ID
import net.ashwork.mc.multilingualexamples.registrar.*
import net.minecraft.data.PackOutput
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraftforge.client.model.generators.ItemModelProvider
import net.minecraftforge.common.data.ExistingFileHelper
import net.minecraftforge.registries.RegistryObject

/**
 * A data provider which generates item models for in this mod.
 *
 * @param output the output of the data generator
 * @param efh a resource holder for linking existing files
 */
internal class ExampleItemModelProvider(output: PackOutput, efh: ExistingFileHelper) : ItemModelProvider(output, ID, efh) {

    override fun registerModels() {
        this.simpleItem(ASH)
        this.simpleItem(COLLAGE_HELMET)
        this.simpleItem(COLLAGE_CHESTPLATE)
        this.simpleItem(COLLAGE_LEGGINGS)
        this.simpleItem(COLLAGE_BOOTS)
        this.simpleItem(WAFFLE_MIX)
        this.simpleItem(WAFFLE_CONE)
        this.simpleItem(SNOW_CONE)
        this.simpleItem(ICE_CREAM_SANDWICH)
    }

    /**
     * Creates a simple item model through the 'item/generated' parent.
     *
     * @param item the item whose model is being generated
     */
    private fun simpleItem(item: RegistryObject<out Item>) = this.simpleItem(item, item.id!!)

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
    private fun simpleItem(item: RegistryObject<out Item>, layer0: ResourceLocation) =
        this.withExistingParent(item.id!!.toString(), "item/generated")
            .texture("layer0", layer0.withPrefix("${this.folder}/"))
}
