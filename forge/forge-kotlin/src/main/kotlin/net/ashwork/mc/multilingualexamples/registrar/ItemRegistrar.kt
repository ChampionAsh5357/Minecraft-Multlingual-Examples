/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.registrar

import net.ashwork.mc.multilingualexamples.ID
import net.ashwork.mc.multilingualexamples.item.CustomArmorModelItem
import net.ashwork.mc.multilingualexamples.item.ExampleArmorMaterials
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject

/**
 * The registrar used to register the items for this mod. Must be attached
 * to the mod event bus through some call within the main mod constructor.
 */
internal val ITEM_REGISTRAR = DeferredRegister.create(ForgeRegistries.ITEMS, ID)

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
