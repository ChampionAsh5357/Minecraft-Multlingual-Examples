/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.registrar

import net.ashwork.mc.multilingualexamples.MultilingualExamples
import net.ashwork.mc.multilingualexamples.item.CustomArmorModelItem
import net.ashwork.mc.multilingualexamples.item.ExampleArmorMaterials
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.{CreativeModeTab, Item}
import net.minecraftforge.registries.{DeferredRegister, ForgeRegistries}

/**
 * A utility class used to hold and register all items for this mod.
 */
object ItemRegistrar {

    /**
     * The registrar used to register the items for this mod. Must be attached
     * to the mod event bus through some call within the main mod constructor.
     */
    final val REGISTRAR = DeferredRegister.create(ForgeRegistries.ITEMS, MultilingualExamples.ID)

    final val ASH = REGISTRAR.register("ash", () => Item(Item.Properties().tab(CreativeModeTab.TAB_BREWING)))
    final val COLLAGE_HELMET = REGISTRAR.register("collage_helmet", () => CustomArmorModelItem(ExampleArmorMaterials.COLLAGE, EquipmentSlot.HEAD, Item.Properties().tab(CreativeModeTab.TAB_COMBAT)))
    final val COLLAGE_CHESTPLATE = REGISTRAR.register("collage_chestplate", () => CustomArmorModelItem(ExampleArmorMaterials.COLLAGE, EquipmentSlot.CHEST, Item.Properties().tab(CreativeModeTab.TAB_COMBAT)))
    final val COLLAGE_LEGGINGS = REGISTRAR.register("collage_leggings", () => CustomArmorModelItem(ExampleArmorMaterials.COLLAGE, EquipmentSlot.LEGS, Item.Properties().tab(CreativeModeTab.TAB_COMBAT)))
    final val COLLAGE_BOOTS = REGISTRAR.register("collage_boots", () => CustomArmorModelItem(ExampleArmorMaterials.COLLAGE, EquipmentSlot.FEET, Item.Properties().tab(CreativeModeTab.TAB_COMBAT)))
}
