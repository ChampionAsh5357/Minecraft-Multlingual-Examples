/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.registrar

import net.ashwork.mc.multilingualexamples.item.CustomArmorModelItem
import net.ashwork.mc.multilingualexamples.item.ExampleArmorMaterials
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraftforge.registries.RegistryObject

/*
 * A utility file used to hold and register all items for this mod.
 */

/**
 * A dummy method used to load the static objects in this class.
 */
internal fun registerItems() = run { }

val ASH: RegistryObject<Item> = ITEM_REGISTRAR.register("ash") {
    Item(Item.Properties().tab(CreativeModeTab.TAB_BREWING))
}
val COLLAGE_HELMET: RegistryObject<Item> = ITEM_REGISTRAR.register("collage_helmet") {
    CustomArmorModelItem(ExampleArmorMaterials.COLLAGE, EquipmentSlot.HEAD, Item.Properties().tab(CreativeModeTab.TAB_COMBAT))
}
val COLLAGE_CHESTPLATE: RegistryObject<Item> = ITEM_REGISTRAR.register("collage_chestplate") {
    CustomArmorModelItem(ExampleArmorMaterials.COLLAGE, EquipmentSlot.CHEST, Item.Properties().tab(CreativeModeTab.TAB_COMBAT))
}
val COLLAGE_LEGGINGS: RegistryObject<Item> = ITEM_REGISTRAR.register("collage_leggings") {
    CustomArmorModelItem(ExampleArmorMaterials.COLLAGE, EquipmentSlot.LEGS, Item.Properties().tab(CreativeModeTab.TAB_COMBAT))
}
val COLLAGE_BOOTS: RegistryObject<Item> = ITEM_REGISTRAR.register("collage_boots") {
    CustomArmorModelItem(ExampleArmorMaterials.COLLAGE, EquipmentSlot.FEET, Item.Properties().tab(CreativeModeTab.TAB_COMBAT))
}
val WAFFLE_MIX: RegistryObject<Item> = ITEM_REGISTRAR.register("waffle_mix") {
    Item(Item.Properties().tab(CreativeModeTab.TAB_FOOD))
}
val WAFFLE_CONE: RegistryObject<Item> = ITEM_REGISTRAR.register("waffle_cone") {
    Item(Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(WAFFLE_CONE_FOOD))
}
val SNOW_CONE: RegistryObject<Item> = ITEM_REGISTRAR.register("snow_cone") {
    Item(Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(SNOW_CONE_FOOD))
}
val ICE_CREAM_SANDWICH: RegistryObject<Item> = ITEM_REGISTRAR.register("ice_cream_sandwich") {
    Item(Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(ICE_CREAM_SANDWICH_FOOD))
}
