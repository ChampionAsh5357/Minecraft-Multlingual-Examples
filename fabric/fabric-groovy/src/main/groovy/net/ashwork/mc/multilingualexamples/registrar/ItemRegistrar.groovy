/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.registrar

import groovy.transform.CompileStatic
import net.ashwork.mc.multilingualexamples.MultilingualExamples
import net.ashwork.mc.multilingualexamples.item.ExampleArmorMaterials
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Item
import org.jetbrains.annotations.ApiStatus
/**
 * A utility class used to hold and register all items for this mod.
 */
@CompileStatic
final class ItemRegistrar {

    /**
     * A dummy method used to load the static objects in this class.
     */
    static void registerItems() {
        BlockRegistrar.registerBlockItems { String name, Item item ->
            register(name, item)
        }

        // Add items to tabs
        ItemGroupEvents.MODIFY_ENTRIES_ALL.register({ tab, FabricItemGroupEntries entries ->
            switch (tab) {
                case CreativeModeTabs.INGREDIENTS -> entries.accept(ASH)
                case CreativeModeTabs.COMBAT -> {
                    entries.accept(COLLAGE_HELMET)
                    entries.accept(COLLAGE_CHESTPLATE)
                    entries.accept(COLLAGE_LEGGINGS)
                    entries.accept(COLLAGE_BOOTS)
                }
                case CreativeModeTabs.FOOD_AND_DRINKS -> {
                    entries.accept(BlockRegistrar.SQUISHED_WAFFLE)
                    entries.accept(BlockRegistrar.WAFFLE)
                    entries.accept(WAFFLE_MIX)
                    entries.accept(WAFFLE_CONE)
                    entries.accept(SNOW_CONE)
                    entries.accept(ICE_CREAM_SANDWICH)
                }
            }
        } as ItemGroupEvents.ModifyEntriesAll)
    }

    private static final List<ArmorItem> CUSTOM_ARMOR_MODEL_ITEMS = []

    /**
     * Registers the renderers for the custom armor model items.
     *
     * @param rendererRegistry the registry holding the renderers
     */
    @ApiStatus.Internal
    static void registerRenderers(Closure<?> rendererRegistry) {
        CUSTOM_ARMOR_MODEL_ITEMS.forEach { rendererRegistry(it) }
    }

    static final Item ASH = register('ash', new Item(new Item.Properties()))
    static final ArmorItem COLLAGE_HELMET = registerCustomArmorModelItem('collage_helmet', new ArmorItem(ExampleArmorMaterials.COLLAGE, EquipmentSlot.HEAD, new Item.Properties()))
    static final ArmorItem COLLAGE_CHESTPLATE = registerCustomArmorModelItem('collage_chestplate', new ArmorItem(ExampleArmorMaterials.COLLAGE, EquipmentSlot.CHEST, new Item.Properties()))
    static final ArmorItem COLLAGE_LEGGINGS = registerCustomArmorModelItem('collage_leggings', new ArmorItem(ExampleArmorMaterials.COLLAGE, EquipmentSlot.LEGS, new Item.Properties()))
    static final ArmorItem COLLAGE_BOOTS = registerCustomArmorModelItem('collage_boots', new ArmorItem(ExampleArmorMaterials.COLLAGE, EquipmentSlot.FEET, new Item.Properties()))
    static final Item WAFFLE_MIX = register('waffle_mix', new Item(new Item.Properties()))
    static final Item WAFFLE_CONE = register('waffle_cone', new Item(new Item.Properties().food(GeneralRegistrar.WAFFLE_CONE)))
    static final Item SNOW_CONE = register('snow_cone', new Item(new Item.Properties().food(GeneralRegistrar.SNOW_CONE)))
    static final Item ICE_CREAM_SANDWICH = register('ice_cream_sandwich', new Item(new Item.Properties().food(GeneralRegistrar.ICE_CREAM_SANDWICH)))

    /**
     * Registers an object to the item registry.
     *
     * @param name the registry name of the object
     * @param obj the object instance
     * @return the object instance being registered
     */
    private static <T extends Item> T register(final String name, final T obj) {
        return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MultilingualExamples.ID, name), obj)
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
        var item = register(name, obj)
        CUSTOM_ARMOR_MODEL_ITEMS.add(item)
        return item
    }
}
