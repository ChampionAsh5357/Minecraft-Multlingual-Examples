/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
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
import java.util.function.Consumer;

/**
 * A utility class used to hold and register all items for this mod.
 */
public final class ItemRegistrar {

    /**
     * A dummy method used to load the static objects in this class.
     */
    public static void register() {
        BlockRegistrar.registerBlockItems(ItemRegistrar::register);
    }

    private static final List<ArmorItem> CUSTOM_ARMOR_MODEL_ITEMS = new ArrayList<>();

    /**
     * Registers the renderers for the custom armor model items.
     *
     * @param rendererRegistry the registry holding the renderers
     */
    @ApiStatus.Internal
    public static void registerRenderers(Consumer<ArmorItem> rendererRegistry) {
        CUSTOM_ARMOR_MODEL_ITEMS.forEach(rendererRegistry);
    }

    public static final Item ASH = register("ash", new Item(new Item.Properties().tab(CreativeModeTab.TAB_BREWING)));
    public static final ArmorItem COLLAGE_HELMET = registerCustomArmorModelItem("collage_helmet", new ArmorItem(ExampleArmorMaterials.COLLAGE, EquipmentSlot.HEAD, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final ArmorItem COLLAGE_CHESTPLATE = registerCustomArmorModelItem("collage_chestplate", new ArmorItem(ExampleArmorMaterials.COLLAGE, EquipmentSlot.CHEST, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final ArmorItem COLLAGE_LEGGINGS = registerCustomArmorModelItem("collage_leggings", new ArmorItem(ExampleArmorMaterials.COLLAGE, EquipmentSlot.LEGS, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final ArmorItem COLLAGE_BOOTS = registerCustomArmorModelItem("collage_boots", new ArmorItem(ExampleArmorMaterials.COLLAGE, EquipmentSlot.FEET, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final Item WAFFLE_MIX = register("waffle_mix", new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD)));
    public static final Item WAFFLE_CONE = register("waffle_cone", new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(GeneralRegistrar.WAFFLE_CONE.get())));
    public static final Item SNOW_CONE = register("snow_cone", new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(GeneralRegistrar.SNOW_CONE.get())));
    public static final Item ICE_CREAM_SANDWICH = register("ice_cream_sandwich", new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(GeneralRegistrar.ICE_CREAM_SANDWICH.get())));

    /**
     * Registers an object to the item registry.
     *
     * @param name the registry name of the object
     * @param obj the object instance
     * @param <T> the type of the object
     * @return the object instance being registered
     */
    private static <T extends Item> T register(final String name, final T obj) {
        return Registry.register(Registry.ITEM, new ResourceLocation(MultilingualExamples.id(), name), obj);
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
