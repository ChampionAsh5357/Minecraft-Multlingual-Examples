/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data

import net.ashwork.mc.multilingualexamples.registrar.*
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider

/**
 * A data provider which generates an 'en_us' localization for the mod.
 *
 * @param gen the generator being written to
 */
class ExampleLocalizationProvider(gen: FabricDataGenerator): FabricLanguageProvider(gen) {

    override fun generateTranslations(builder: TranslationBuilder) {
        // Add items
        builder.add(ASH, "Ash")
        builder.add(COLLAGE_HELMET, "Hat")
        builder.add(COLLAGE_CHESTPLATE, "Rocket")
        builder.add(COLLAGE_LEGGINGS, "Belt")
        builder.add(COLLAGE_BOOTS, "Ankle Guard")
        builder.add(WAFFLE_MIX, "Waffle Mix")
        builder.add(WAFFLE_CONE, "Waffle Cone")
        builder.add(SNOW_CONE, "Snow Cone")
        builder.add(ICE_CREAM_SANDWICH, "Ice Cream Sandwich")

        // Add blocks
        builder.add(WAFFLE, "Waffle")
        builder.add(SQUISHED_WAFFLE, "Squished Waffle")
    }
}
