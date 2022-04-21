/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.registrar

import net.ashwork.mc.multilingualexamples.MultilingualExamples
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.{CreativeModeTab, Item}

/**
 * A utility class used to hold and register all items for this mod.
 */
object ItemRegistrar {

    final val ASH = register("ash", new Item(new Item.Properties().tab(CreativeModeTab.TAB_BREWING)))

    /**
     * Registers an object to the item registry.
     *
     * @param name the registry name of the object
     * @param obj the object instance
     * @tparam T the type of the object
     * @return the object instance being registered
     */
    private def register[T <: Item](name: String, obj: T): T =
        Registry.register(Registry.ITEM, new ResourceLocation(MultilingualExamples.ID, name), obj)
}
