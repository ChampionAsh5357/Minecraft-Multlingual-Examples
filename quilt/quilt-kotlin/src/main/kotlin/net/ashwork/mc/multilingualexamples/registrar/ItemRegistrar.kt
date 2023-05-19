/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.registrar

import net.ashwork.mc.multilingualexamples.id
import net.ashwork.mc.multilingualexamples.item.ExampleArmorMaterials
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Item
import org.jetbrains.annotations.ApiStatus

/**
 * A dummy method used to load the static objects in this class.
 */
fun registerItems() {
    registerBlockItems { name, obj -> register(name, obj) }

    // Add items to tabs
    ItemGroupEvents.MODIFY_ENTRIES_ALL.register { tab, entries ->
        when (tab) {
            CreativeModeTabs.INGREDIENTS -> entries.accept(ASH)
            CreativeModeTabs.COMBAT -> {
                entries.accept(COLLAGE_HELMET)
                entries.accept(COLLAGE_CHESTPLATE)
                entries.accept(COLLAGE_LEGGINGS)
                entries.accept(COLLAGE_BOOTS)
            }
            CreativeModeTabs.FOOD_AND_DRINKS -> {
                entries.accept(SQUISHED_WAFFLE)
                entries.accept(WAFFLE)
                entries.accept(WAFFLE_MIX)
                entries.accept(WAFFLE_CONE)
                entries.accept(SNOW_CONE)
                entries.accept(ICE_CREAM_SANDWICH)
            }
        }
    }
}

private val customArmorModelItems: MutableList<ArmorItem> = mutableListOf()

/**
 * Registers the renderers for the custom armor model items.
 *
 * @param rendererRegistry the registry holding the renderers
 */
@ApiStatus.Internal
fun registerRenderers(rendererRegistry: (ArmorItem) -> Unit) = customArmorModelItems.forEach(rendererRegistry)

val ASH: Item = register("ash", Item(Item.Properties()))
val COLLAGE_HELMET: ArmorItem = registerCustomArmorModelItem("collage_helmet", ArmorItem(ExampleArmorMaterials.COLLAGE, ArmorItem.Type.HELMET, Item.Properties()))
val COLLAGE_CHESTPLATE: ArmorItem = registerCustomArmorModelItem("collage_chestplate", ArmorItem(ExampleArmorMaterials.COLLAGE, ArmorItem.Type.CHESTPLATE, Item.Properties()))
val COLLAGE_LEGGINGS: ArmorItem = registerCustomArmorModelItem("collage_leggings", ArmorItem(ExampleArmorMaterials.COLLAGE, ArmorItem.Type.LEGGINGS, Item.Properties()))
val COLLAGE_BOOTS: ArmorItem = registerCustomArmorModelItem("collage_boots", ArmorItem(ExampleArmorMaterials.COLLAGE, ArmorItem.Type.BOOTS, Item.Properties()))
val WAFFLE_MIX = register("waffle_mix", Item(Item.Properties()))
val WAFFLE_CONE = register("waffle_cone", Item(Item.Properties().food(WAFFLE_CONE_FOOD)))
val SNOW_CONE = register("snow_cone", Item(Item.Properties().food(SNOW_CONE_FOOD)))
val ICE_CREAM_SANDWICH = register("ice_cream_sandwich", Item(Item.Properties().food(ICE_CREAM_SANDWICH_FOOD)))

/**
 * Registers an object to the item registry.
 *
 * @param name the registry name of the object
 * @param obj the object instance
 * @param T the type of the object
 * @return the object instance being registered
 */
private fun <T: Item> register(name: String, obj: T): T =
    Registry.register(BuiltInRegistries.ITEM, ResourceLocation(id(), name), obj)

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
