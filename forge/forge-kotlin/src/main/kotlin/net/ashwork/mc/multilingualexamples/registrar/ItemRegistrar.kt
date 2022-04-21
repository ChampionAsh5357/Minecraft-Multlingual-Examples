/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.registrar

import net.ashwork.mc.multilingualexamples.ID
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