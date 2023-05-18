/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.registrar

import net.ashwork.mc.multilingualexamples.MultilingualExamples
import net.ashwork.mc.multilingualexamples.item.ExampleArmorMaterials
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.{ArmorItem, CreativeModeTab, Item}
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
    }

    private final val CUSTOM_ARMOR_MODEL_ITEMS: ListBuffer[ArmorItem] = ListBuffer()

    /**
     * Registers the renderers for the custom armor model items.
     *
     * @param rendererRegistry the registry holding the renderers
     */
    @Internal
    def registerRenderers(rendererRegistry: ArmorItem => Unit): Unit = CUSTOM_ARMOR_MODEL_ITEMS.foreach(rendererRegistry)

    final val ASH = register("ash", Item(Item.Properties().tab(CreativeModeTab.TAB_BREWING)))
    final val COLLAGE_HELMET = registerCustomArmorModelItem("collage_helmet", ArmorItem(ExampleArmorMaterials.COLLAGE, EquipmentSlot.HEAD, Item.Properties().tab(CreativeModeTab.TAB_COMBAT)))
    final val COLLAGE_CHESTPLATE = registerCustomArmorModelItem("collage_chestplate", ArmorItem(ExampleArmorMaterials.COLLAGE, EquipmentSlot.CHEST, Item.Properties().tab(CreativeModeTab.TAB_COMBAT)))
    final val COLLAGE_LEGGINGS = registerCustomArmorModelItem("collage_leggings", ArmorItem(ExampleArmorMaterials.COLLAGE, EquipmentSlot.LEGS, Item.Properties().tab(CreativeModeTab.TAB_COMBAT)))
    final val COLLAGE_BOOTS = registerCustomArmorModelItem("collage_boots", ArmorItem(ExampleArmorMaterials.COLLAGE, EquipmentSlot.FEET, Item.Properties().tab(CreativeModeTab.TAB_COMBAT)))
    final val WAFFLE_MIX = register("waffle_mix", Item(Item.Properties().tab(CreativeModeTab.TAB_FOOD)))
    final val WAFFLE_CONE = register("waffle_cone", Item(Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(GeneralRegistrar.WAFFLE_CONE)))
    final val SNOW_CONE = register("snow_cone", Item(Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(GeneralRegistrar.SNOW_CONE)))
    final val ICE_CREAM_SANDWICH = register("ice_cream_sandwich", Item(Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(GeneralRegistrar.ICE_CREAM_SANDWICH)))

    /**
     * Registers an object to the item registry.
     *
     * @param name the registry name of the object
     * @param obj the object instance
     * @tparam T the type of the object
     * @return the object instance being registered
     */
    private def register[T <: Item](name: String, obj: T): T =
        Registry.register(Registry.ITEM, ResourceLocation(MultilingualExamples.ID, name), obj)

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
