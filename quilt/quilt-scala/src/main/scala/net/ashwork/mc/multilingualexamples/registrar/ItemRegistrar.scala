/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.registrar

import net.ashwork.mc.multilingualexamples.MultilingualExamples
import net.ashwork.mc.multilingualexamples.item.ExampleArmorMaterials
import net.ashwork.mc.multilingualexamples.registrar.ItemRegistrar.registerCustomArmorModelItem
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.{ArmorItem, CreativeModeTab, CreativeModeTabs, Item}
import org.jetbrains.annotations.ApiStatus.Internal

import scala.collection.mutable.ListBuffer

/**
 * A utility class used to hold and register all items for this mod.
 */
object ItemRegistrar {

    /**
     * A dummy method used to load the static objects in this class.
     */
    def register(): Unit = {
        BlockRegistrar.registerBlockItems(ItemRegistrar.register(_, _))

        // Add items to tabs
        ItemGroupEvents.MODIFY_ENTRIES_ALL.register((tab, entries) => {
            tab match
                case CreativeModeTabs.INGREDIENTS => entries.accept(ASH)
                case CreativeModeTabs.COMBAT =>
                    entries.accept(COLLAGE_HELMET)
                    entries.accept(COLLAGE_CHESTPLATE)
                    entries.accept(COLLAGE_LEGGINGS)
                    entries.accept(COLLAGE_BOOTS)
                case CreativeModeTabs.FOOD_AND_DRINKS =>
                    entries.accept(BlockRegistrar.SQUISHED_WAFFLE)
                    entries.accept(BlockRegistrar.WAFFLE)
                    entries.accept(WAFFLE_MIX)
                    entries.accept(WAFFLE_CONE)
                    entries.accept(SNOW_CONE)
                    entries.accept(ICE_CREAM_SANDWICH)
                case _ =>
        })
    }

    private final val CUSTOM_ARMOR_MODEL_ITEMS: ListBuffer[ArmorItem] = ListBuffer()

    /**
     * Registers the renderers for the custom armor model items.
     *
     * @param rendererRegistry the registry holding the renderers
     */
    @Internal
    def registerRenderers(rendererRegistry: ArmorItem => Unit): Unit = CUSTOM_ARMOR_MODEL_ITEMS.foreach(rendererRegistry)

    final val ASH = register("ash", new Item(new Item.Properties()))
    final val COLLAGE_HELMET = registerCustomArmorModelItem("collage_helmet", ArmorItem(ExampleArmorMaterials.COLLAGE, ArmorItem.Type.HELMET, Item.Properties()))
    final val COLLAGE_CHESTPLATE = registerCustomArmorModelItem("collage_chestplate", ArmorItem(ExampleArmorMaterials.COLLAGE, ArmorItem.Type.CHESTPLATE, Item.Properties()))
    final val COLLAGE_LEGGINGS = registerCustomArmorModelItem("collage_leggings", ArmorItem(ExampleArmorMaterials.COLLAGE, ArmorItem.Type.LEGGINGS, Item.Properties()))
    final val COLLAGE_BOOTS = registerCustomArmorModelItem("collage_boots", ArmorItem(ExampleArmorMaterials.COLLAGE, ArmorItem.Type.BOOTS, Item.Properties()))
    final val WAFFLE_MIX = register("waffle_mix", Item(Item.Properties()))
    final val WAFFLE_CONE = register("waffle_cone", Item(Item.Properties().food(GeneralRegistrar.WAFFLE_CONE)))
    final val SNOW_CONE = register("snow_cone", Item(Item.Properties().food(GeneralRegistrar.SNOW_CONE)))
    final val ICE_CREAM_SANDWICH = register("ice_cream_sandwich", Item(Item.Properties().food(GeneralRegistrar.ICE_CREAM_SANDWICH)))

    /**
     * Registers an object to the item registry.
     *
     * @param name the registry name of the object
     * @param obj the object instance
     * @tparam T the type of the object
     * @return the object instance being registered
     */
    private def register[T <: Item](name: String, obj: T): T =
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation(MultilingualExamples.id(), name), obj)

    /**
     * Registers an armor item to be rendered with a custom model.
     *
     * @param name the registry name of the object
     * @param obj the object instance
     * @tparam T the type of the object
     * @return the object instance being registered
     */
    private def registerCustomArmorModelItem[T <: ArmorItem](name: String, obj: T): T = {
        val item = register(name, obj)
        CUSTOM_ARMOR_MODEL_ITEMS.addOne(item)
        item
    }
}
