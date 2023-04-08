/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data

import net.ashwork.mc.multilingualexamples.registrar.{BlockRegistrar, ItemRegistrar}
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider

/**
 * A data provider which generates an 'en_us' localization for the mod.
 *
 * @param gen the generator being written to
 */
class ExampleLocalizationProvider(gen: FabricDataGenerator) extends FabricLanguageProvider(gen) {

    override def generateTranslations(builder: FabricLanguageProvider.TranslationBuilder): Unit = {
        // Add items
        builder.add(ItemRegistrar.ASH, "Ash")
        builder.add(ItemRegistrar.COLLAGE_HELMET, "Hat")
        builder.add(ItemRegistrar.COLLAGE_CHESTPLATE, "Rocket")
        builder.add(ItemRegistrar.COLLAGE_LEGGINGS, "Belt")
        builder.add(ItemRegistrar.COLLAGE_BOOTS, "Ankle Guard")
        builder.add(ItemRegistrar.WAFFLE_MIX, "Waffle Mix")
        builder.add(ItemRegistrar.WAFFLE_CONE, "Waffle Cone")
        builder.add(ItemRegistrar.SNOW_CONE, "Snow Cone")
        builder.add(ItemRegistrar.ICE_CREAM_SANDWICH, "Ice Cream Sandwich")

        // Add blocks
        builder.add(BlockRegistrar.WAFFLE, "Waffle")
        builder.add(BlockRegistrar.SQUISHED_WAFFLE, "Squished Waffle")
    }
}
