/*
 * Multilingual Examples
 * Written 2021-2021 by ChampionAsh5357.
 *
 * To the extent possible under law, the author(s) have dedicated all copyright and related
 * and neighboring rights to this software to the public domain worldwide. This software is
 * distributed without any warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication along with this
 * software. If not, see https://creativecommons.org/publicdomain/zero/1.0/.
 */

package net.ashwork.multilingualexamples.data

import net.ashwork.multilingualexamples.MultilingualExamples
import net.ashwork.multilingualexamples.registrar.ItemRegistrar
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
class ItemModels(gen: DataGenerator, efh: ExistingFileHelper) extends ItemModelProvider(gen, MultilingualExamples.ID, efh) with ModelProviderExtension {

    override def registerModels(): Unit = {
        this.simpleItem(ItemRegistrar.ASH.get)
    }

    /**
     * Creates a simple item model through the 'item/generated' parent.
     *
     * @param item the item whose model is being generated
     */
    private def simpleItem(item: () => Item): Unit = this.simpleItem(item, item.apply.getRegistryName)

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
    private def simpleItem(item: () => Item, layer0: ResourceLocation) =
        this.withExistingParent(item.apply().getRegistryName.toString, "item/generated")
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
