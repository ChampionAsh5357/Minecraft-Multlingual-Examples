/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.registrar;

import net.ashwork.mc.multilingualexamples.MultilingualExamples;
import net.ashwork.mc.multilingualexamples.item.CustomArmorModelItem;
import net.ashwork.mc.multilingualexamples.item.ExampleArmorMaterials;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * A utility class used to hold and register all items for this mod.
 */
public final class ItemRegistrar {

    /**
     * The registrar used to register the items for this mod. Must be attached
     * to the mod event bus through some call within the main mod constructor.
     */
    public static final DeferredRegister<Item> REGISTRAR = DeferredRegister.create(ForgeRegistries.ITEMS, MultilingualExamples.ID);

    public static final RegistryObject<Item> ASH = REGISTRAR.register("ash", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_BREWING)));

    public static final RegistryObject<Item> COLLAGE_HELMET = REGISTRAR.register("collage_helmet", () -> new CustomArmorModelItem(ExampleArmorMaterials.COLLAGE, EquipmentSlot.HEAD, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> COLLAGE_CHESTPLATE = REGISTRAR.register("collage_chestplate", () -> new CustomArmorModelItem(ExampleArmorMaterials.COLLAGE, EquipmentSlot.CHEST, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> COLLAGE_LEGGINGS = REGISTRAR.register("collage_leggings", () -> new CustomArmorModelItem(ExampleArmorMaterials.COLLAGE, EquipmentSlot.LEGS, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> COLLAGE_BOOTS = REGISTRAR.register("collage_boots", () -> new CustomArmorModelItem(ExampleArmorMaterials.COLLAGE, EquipmentSlot.FEET, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
}
