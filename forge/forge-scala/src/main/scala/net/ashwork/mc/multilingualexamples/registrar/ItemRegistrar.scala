/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.registrar

import net.ashwork.mc.multilingualexamples.MultilingualExamples
import net.ashwork.mc.multilingualexamples.item.CustomArmorModelItem
import net.ashwork.mc.multilingualexamples.item.ExampleArmorMaterials
import net.minecraft.world.item.{ArmorItem, Item}
import net.minecraftforge.registries.{DeferredRegister, ForgeRegistries}

/**
 * A utility object used to hold and register all items for this mod.
 */
object ItemRegistrar {

    /**
     * A dummy method used to load the static objects in this class.
     */
    def register(): Unit = {}

    final val ASH = Registrars.ITEM_REGISTRAR.register("ash", () => Item(Item.Properties()))
    final val COLLAGE_HELMET = Registrars.ITEM_REGISTRAR.register("collage_helmet", () => CustomArmorModelItem(ExampleArmorMaterials.COLLAGE, ArmorItem.Type.HELMET, Item.Properties()))
    final val COLLAGE_CHESTPLATE = Registrars.ITEM_REGISTRAR.register("collage_chestplate", () => CustomArmorModelItem(ExampleArmorMaterials.COLLAGE, ArmorItem.Type.CHESTPLATE, Item.Properties()))
    final val COLLAGE_LEGGINGS = Registrars.ITEM_REGISTRAR.register("collage_leggings", () => CustomArmorModelItem(ExampleArmorMaterials.COLLAGE, ArmorItem.Type.LEGGINGS, Item.Properties()))
    final val COLLAGE_BOOTS = Registrars.ITEM_REGISTRAR.register("collage_boots", () => CustomArmorModelItem(ExampleArmorMaterials.COLLAGE, ArmorItem.Type.BOOTS, Item.Properties()))
    final val WAFFLE_MIX = Registrars.ITEM_REGISTRAR.register("waffle_mix", () => Item(Item.Properties()))
    final val WAFFLE_CONE = Registrars.ITEM_REGISTRAR.register("waffle_cone", () => Item(Item.Properties().food(GeneralRegistrar.WAFFLE_CONE)))
    final val SNOW_CONE = Registrars.ITEM_REGISTRAR.register("snow_cone", () => Item(Item.Properties().food(GeneralRegistrar.SNOW_CONE)))
    final val ICE_CREAM_SANDWICH = Registrars.ITEM_REGISTRAR.register("ice_cream_sandwich", () => Item(Item.Properties().food(GeneralRegistrar.ICE_CREAM_SANDWICH)))
}
