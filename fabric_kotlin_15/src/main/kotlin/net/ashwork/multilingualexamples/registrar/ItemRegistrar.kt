package net.ashwork.multilingualexamples.registrar

import net.ashwork.multilingualexamples.ID
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item

val ASH: Item = register("ash", Item(Item.Properties().tab(CreativeModeTab.TAB_BREWING)))

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