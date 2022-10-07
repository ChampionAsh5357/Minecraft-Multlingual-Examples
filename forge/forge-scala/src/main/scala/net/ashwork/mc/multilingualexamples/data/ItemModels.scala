/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data

import net.ashwork.mc.multilingualexamples.MultilingualExamples
import net.ashwork.mc.multilingualexamples.registrar.ItemRegistrar
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
class ItemModels(gen: DataGenerator, efh: ExistingFileHelper) extends ItemModelProvider(gen, MultilingualExamples.ID, efh) with ModelProviderExtension {

    override def registerModels(): Unit = {
        this.simpleItem(ItemRegistrar.ASH)
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
                .texture("layer0", this.prefix(layer0, this.folder))
}

/**
 * The global instance of [[ItemModels]]. Created for apply factories.
 */
object ItemModels {

    /**
     * A simple constructor.
     *
     * @param gen the generator being written to
     * @param efh a resource holder for linking existing files
     * @return a new [[ItemModels]] instance
     */
    def apply(gen: DataGenerator, efh: ExistingFileHelper): ItemModels = new ItemModels(gen, efh)
}
