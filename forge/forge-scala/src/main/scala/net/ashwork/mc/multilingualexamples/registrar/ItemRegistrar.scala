/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
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
 * A utility object used to hold and register all items for this mod.
 */
object ItemRegistrar {

    /**
     * A dummy method used to load the static objects in this class.
     */
    def register(): Unit = {}

    final val ASH = Registrars.ITEM_REGISTRAR.register("ash", () => Item(Item.Properties().tab(CreativeModeTab.TAB_BREWING)))
    final val COLLAGE_HELMET = Registrars.ITEM_REGISTRAR.register("collage_helmet", () => CustomArmorModelItem(ExampleArmorMaterials.COLLAGE, EquipmentSlot.HEAD, Item.Properties().tab(CreativeModeTab.TAB_COMBAT)))
    final val COLLAGE_CHESTPLATE = Registrars.ITEM_REGISTRAR.register("collage_chestplate", () => CustomArmorModelItem(ExampleArmorMaterials.COLLAGE, EquipmentSlot.CHEST, Item.Properties().tab(CreativeModeTab.TAB_COMBAT)))
    final val COLLAGE_LEGGINGS = Registrars.ITEM_REGISTRAR.register("collage_leggings", () => CustomArmorModelItem(ExampleArmorMaterials.COLLAGE, EquipmentSlot.LEGS, Item.Properties().tab(CreativeModeTab.TAB_COMBAT)))
    final val COLLAGE_BOOTS = Registrars.ITEM_REGISTRAR.register("collage_boots", () => CustomArmorModelItem(ExampleArmorMaterials.COLLAGE, EquipmentSlot.FEET, Item.Properties().tab(CreativeModeTab.TAB_COMBAT)))
    final val WAFFLE_MIX = Registrars.ITEM_REGISTRAR.register("waffle_mix", () => Item(Item.Properties().tab(CreativeModeTab.TAB_FOOD)))
    final val WAFFLE_CONE = Registrars.ITEM_REGISTRAR.register("waffle_cone", () => Item(Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(GeneralRegistrar.WAFFLE_CONE)))
    final val SNOW_CONE = Registrars.ITEM_REGISTRAR.register("snow_cone", () => Item(Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(GeneralRegistrar.SNOW_CONE)))
    final val ICE_CREAM_SANDWICH = Registrars.ITEM_REGISTRAR.register("ice_cream_sandwich", () => Item(Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(GeneralRegistrar.ICE_CREAM_SANDWICH)))
}
