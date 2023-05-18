/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.registrar

import net.ashwork.mc.multilingualexamples.ID
import net.ashwork.mc.multilingualexamples.item.ExampleArmorMaterials
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import org.jetbrains.annotations.ApiStatus.Internal

/**
 * A dummy method used to load the static objects in this class.
 */
fun registerItems() {
    registerBlockItems { name, obj -> register(name, obj) }
}

private val customArmorModelItems: MutableList<ArmorItem> = mutableListOf()

/**
 * Registers the renderers for the custom armor model items.
 *
 * @param rendererRegistry the registry holding the renderers
 */
@Internal
fun registerRenderers(rendererRegistry: (ArmorItem) -> Unit) = customArmorModelItems.forEach(rendererRegistry)

val ASH: Item = register("ash", Item(Item.Properties().tab(CreativeModeTab.TAB_BREWING)))
val COLLAGE_HELMET: ArmorItem = registerCustomArmorModelItem("collage_helmet", ArmorItem(ExampleArmorMaterials.COLLAGE, EquipmentSlot.HEAD, Item.Properties().tab(CreativeModeTab.TAB_COMBAT)))
val COLLAGE_CHESTPLATE: ArmorItem = registerCustomArmorModelItem("collage_chestplate", ArmorItem(ExampleArmorMaterials.COLLAGE, EquipmentSlot.CHEST, Item.Properties().tab(CreativeModeTab.TAB_COMBAT)))
val COLLAGE_LEGGINGS: ArmorItem = registerCustomArmorModelItem("collage_leggings", ArmorItem(ExampleArmorMaterials.COLLAGE, EquipmentSlot.LEGS, Item.Properties().tab(CreativeModeTab.TAB_COMBAT)))
val COLLAGE_BOOTS: ArmorItem = registerCustomArmorModelItem("collage_boots", ArmorItem(ExampleArmorMaterials.COLLAGE, EquipmentSlot.FEET, Item.Properties().tab(CreativeModeTab.TAB_COMBAT)))
val WAFFLE_MIX = register("waffle_mix", Item(Item.Properties().tab(CreativeModeTab.TAB_FOOD)))
val WAFFLE_CONE = register("waffle_cone", Item(Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(WAFFLE_CONE_FOOD)))
val SNOW_CONE = register("snow_cone", Item(Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(SNOW_CONE_FOOD)))
val ICE_CREAM_SANDWICH = register("ice_cream_sandwich", Item(Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(ICE_CREAM_SANDWICH_FOOD)))

/**
 * Registers an object to the item registry.
 *
 * @param name the registry name of the object
 * @param obj the object instance
 * @param T the type of the object
 * @return the object instance being registered
 */
private fun <T: Item> register(name: String, obj: T): T =
    Registry.register(Registry.ITEM, ResourceLocation(ID, name), obj)

/**
 * Registers an armor item to be rendered with a custom model.
 *
 * @param name the registry name of the object
 * @param obj the object instance
 * @param T the type of the object
 * @return the object instance being registered
 */
private fun <T: ArmorItem> registerCustomArmorModelItem(name: String, obj: T): T {
    val item = register(name, obj)
    customArmorModelItems.add(item)
    return item
}
