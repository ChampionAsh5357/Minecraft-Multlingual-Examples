/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data

import net.ashwork.mc.multilingualexamples.MultilingualExamples
import net.ashwork.mc.multilingualexamples.registrar.ItemRegistrar
import net.ashwork.mc.multilingualexamples.util.MultilingualExtensions.prefix
import net.minecraft.data.DataGenerator
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraftforge.client.model.generators.ItemModelProvider
import net.minecraftforge.common.data.ExistingFileHelper
import net.minecraftforge.registries.RegistryObject

/**
 * A data provider which generates item models for the mod.
 *
 * @param gen the generator being written to
 * @param efh a resource holder for linking existing files
 */
class ExampleItemModelProvider(gen: DataGenerator, efh: ExistingFileHelper) extends ItemModelProvider(gen, MultilingualExamples.ID, efh) {

    override def registerModels(): Unit = {
        this.simpleItem(ItemRegistrar.ASH)
        this.simpleItem(ItemRegistrar.COLLAGE_HELMET)
        this.simpleItem(ItemRegistrar.COLLAGE_CHESTPLATE)
        this.simpleItem(ItemRegistrar.COLLAGE_LEGGINGS)
        this.simpleItem(ItemRegistrar.COLLAGE_BOOTS)
        this.simpleItem(ItemRegistrar.WAFFLE_MIX)
        this.simpleItem(ItemRegistrar.WAFFLE_CONE)
        this.simpleItem(ItemRegistrar.SNOW_CONE)
        this.simpleItem(ItemRegistrar.ICE_CREAM_SANDWICH)
    }

    /**
     * Creates a simple item model through the 'item/generated' parent.
     *
     * @param item the item whose model is being generated
     */
    private def simpleItem(item: RegistryObject[_ <: Item]): Unit = this.simpleItem(item, item.getId)

    /**
     * Creates a simple item model through the 'item/generated' parent with a single
     * layer.
     *
     * 'layer0' should point to a generalized resource location. The item subdirectory
     * transformation is done within this method.
     *
     * @param item the item whose model is being generated
     * @param layer0 the name of the layer0 texture living in 'textures/item'
     */
    private def simpleItem(item: RegistryObject[_ <: Item], layer0: ResourceLocation): Unit =
        this.withExistingParent(item.getId.toString, "item/generated")
                .texture("layer0", layer0 prefix this.folder)
}
