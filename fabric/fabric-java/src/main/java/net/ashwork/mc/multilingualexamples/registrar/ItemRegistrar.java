/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.registrar;

import net.ashwork.mc.multilingualexamples.MultilingualExamples;
import net.ashwork.mc.multilingualexamples.item.ExampleArmorMaterials;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * A utility class used to hold and register all items for this mod.
 */
public final class ItemRegistrar {

    @ApiStatus.Internal
    public static final List<ArmorItem> CUSTOM_ARMOR_MODEL_ITEMS = new ArrayList<>();

    public static final Item ASH = register("ash", new Item(new Item.Properties().tab(CreativeModeTab.TAB_BREWING)));
    public static final ArmorItem COLLAGE_HELMET = registerCustomArmorModelItem("collage_helmet", new ArmorItem(ExampleArmorMaterials.COLLAGE, EquipmentSlot.HEAD, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final ArmorItem COLLAGE_CHESTPLATE = registerCustomArmorModelItem("collage_chestplate", new ArmorItem(ExampleArmorMaterials.COLLAGE, EquipmentSlot.CHEST, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final ArmorItem COLLAGE_LEGGINGS = registerCustomArmorModelItem("collage_leggings", new ArmorItem(ExampleArmorMaterials.COLLAGE, EquipmentSlot.LEGS, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final ArmorItem COLLAGE_BOOTS = registerCustomArmorModelItem("collage_boots", new ArmorItem(ExampleArmorMaterials.COLLAGE, EquipmentSlot.FEET, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    /**
     * Registers an object to the item registry.
     *
     * @param name the registry name of the object
     * @param obj the object instance
     * @param <T> the type of the object
     * @return the object instance being registered
     */
    private static <T extends Item> T register(final String name, final T obj) {
        return Registry.register(Registry.ITEM, new ResourceLocation(MultilingualExamples.ID, name), obj);
    }

    /**
     * Registers an armor item to be rendered with a custom model.
     *
     * @param name the registry name of the object
     * @param obj the object instance
     * @param <T> the type of the object
     * @return the object instance being registered
     */
    private static <T extends ArmorItem> T registerCustomArmorModelItem(final String name, final T obj) {
        var item = register(name, obj);
        CUSTOM_ARMOR_MODEL_ITEMS.add(item);
        return item;
    }
}
