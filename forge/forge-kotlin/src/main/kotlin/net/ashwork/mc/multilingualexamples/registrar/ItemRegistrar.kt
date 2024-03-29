/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.registrar

import net.ashwork.mc.multilingualexamples.item.CustomArmorModelItem
import net.ashwork.mc.multilingualexamples.item.ExampleArmorMaterials
import net.minecraft.world.item.ArmorItem
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
    Item(Item.Properties())
}
val COLLAGE_HELMET: RegistryObject<Item> = ITEM_REGISTRAR.register("collage_helmet") {
    CustomArmorModelItem(ExampleArmorMaterials.COLLAGE, ArmorItem.Type.HELMET, Item.Properties())
}
val COLLAGE_CHESTPLATE: RegistryObject<Item> = ITEM_REGISTRAR.register("collage_chestplate") {
    CustomArmorModelItem(ExampleArmorMaterials.COLLAGE, ArmorItem.Type.CHESTPLATE, Item.Properties())
}
val COLLAGE_LEGGINGS: RegistryObject<Item> = ITEM_REGISTRAR.register("collage_leggings") {
    CustomArmorModelItem(ExampleArmorMaterials.COLLAGE, ArmorItem.Type.LEGGINGS, Item.Properties())
}
val COLLAGE_BOOTS: RegistryObject<Item> = ITEM_REGISTRAR.register("collage_boots") {
    CustomArmorModelItem(ExampleArmorMaterials.COLLAGE, ArmorItem.Type.BOOTS, Item.Properties())
}
val WAFFLE_MIX: RegistryObject<Item> = ITEM_REGISTRAR.register("waffle_mix") {
    Item(Item.Properties())
}
val WAFFLE_CONE: RegistryObject<Item> = ITEM_REGISTRAR.register("waffle_cone") {
    Item(Item.Properties().food(WAFFLE_CONE_FOOD))
}
val SNOW_CONE: RegistryObject<Item> = ITEM_REGISTRAR.register("snow_cone") {
    Item(Item.Properties().food(SNOW_CONE_FOOD))
}
val ICE_CREAM_SANDWICH: RegistryObject<Item> = ITEM_REGISTRAR.register("ice_cream_sandwich") {
    Item(Item.Properties().food(ICE_CREAM_SANDWICH_FOOD))
}
