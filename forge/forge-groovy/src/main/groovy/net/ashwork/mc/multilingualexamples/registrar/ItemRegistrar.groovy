/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.registrar

import groovy.transform.CompileStatic
import net.ashwork.mc.multilingualexamples.item.CustomArmorModelItem
import net.ashwork.mc.multilingualexamples.item.ExampleArmorMaterials
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraftforge.registries.RegistryObject
/**
 * A utility class used to hold and register all items for this mod.
 */
@CompileStatic
final class ItemRegistrar {

    /**
     * A dummy method used to load the static objects in this class.
     */
    static void register() {}

    static final RegistryObject<Item> ASH = Registrars.ITEMS.register('ash') {
        new Item(new Item.Properties().tab(CreativeModeTab.TAB_BREWING))
    }
    static final RegistryObject<CustomArmorModelItem> COLLAGE_HELMET = Registrars.ITEMS.register('collage_helmet') {
        new CustomArmorModelItem(ExampleArmorMaterials.COLLAGE, EquipmentSlot.HEAD, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT))
    }
    static final RegistryObject<CustomArmorModelItem> COLLAGE_CHESTPLATE = Registrars.ITEMS.register('collage_chestplate') {
        new CustomArmorModelItem(ExampleArmorMaterials.COLLAGE, EquipmentSlot.CHEST, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT))
    }
    static final RegistryObject<CustomArmorModelItem> COLLAGE_LEGGINGS = Registrars.ITEMS.register('collage_leggings') {
        new CustomArmorModelItem(ExampleArmorMaterials.COLLAGE, EquipmentSlot.LEGS, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT))
    }
    static final RegistryObject<CustomArmorModelItem> COLLAGE_BOOTS = Registrars.ITEMS.register('collage_boots') {
        new CustomArmorModelItem(ExampleArmorMaterials.COLLAGE, EquipmentSlot.FEET, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT))
    }
    static final RegistryObject<Item> WAFFLE_MIX = Registrars.ITEMS.register("waffle_mix") {
        new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD))
    }
    static final RegistryObject<Item> WAFFLE_CONE = Registrars.ITEMS.register("waffle_cone") {
        new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(GeneralRegistrar.WAFFLE_CONE))
    }
    static final RegistryObject<Item> SNOW_CONE = Registrars.ITEMS.register("snow_cone") {
        new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(GeneralRegistrar.SNOW_CONE))
    }
    static final RegistryObject<Item> ICE_CREAM_SANDWICH = Registrars.ITEMS.register("ice_cream_sandwich") {
        new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(GeneralRegistrar.ICE_CREAM_SANDWICH))
    }
}
