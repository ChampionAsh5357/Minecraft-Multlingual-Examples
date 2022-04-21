/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.registrar

import net.ashwork.mc.multilingualexamples.MultilingualExamples
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject

/**
 * A utility class used to hold and register all items for this mod.
 */
final class ItemRegistrar {

    /**
     * The registrar used to register the items for this mod. Must be attached
     * to the mod event bus through some call within the main mod constructor.
     */
    static final DeferredRegister<Item> REGISTRAR = DeferredRegister.create(ForgeRegistries.ITEMS, MultilingualExamples.ID)

    static final RegistryObject<Item> ASH = REGISTRAR.register('ash') {
        new Item(new Item.Properties().tab(CreativeModeTab.TAB_BREWING))
    }
}
