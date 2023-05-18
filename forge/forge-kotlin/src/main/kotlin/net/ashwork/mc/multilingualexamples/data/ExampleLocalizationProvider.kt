/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data

import net.ashwork.mc.multilingualexamples.ID
import net.ashwork.mc.multilingualexamples.registrar.*
import net.minecraft.data.DataGenerator
import net.minecraftforge.common.data.LanguageProvider

/**
 * A data provider which generates an 'en_us' localization for the mod.
 *
 * @param gen the generator being written to
 */
internal class ExampleLocalizationProvider(gen: DataGenerator) : LanguageProvider(gen, ID, "en_us") {

    override fun addTranslations() {
        // Add items
        this.addItem(ASH, "Ash")
        this.addItem(COLLAGE_HELMET, "Hat")
        this.addItem(COLLAGE_CHESTPLATE, "Rocket")
        this.addItem(COLLAGE_LEGGINGS, "Belt")
        this.addItem(COLLAGE_BOOTS, "Ankle Guard")
        this.addItem(WAFFLE_MIX, "Waffle Mix")
        this.addItem(WAFFLE_CONE, "Waffle Cone")
        this.addItem(SNOW_CONE, "Snow Cone")
        this.addItem(ICE_CREAM_SANDWICH, "Ice Cream Sandwich")

        // Add blocks
        this.addBlock(WAFFLE, "Waffle")
        this.addBlock(SQUISHED_WAFFLE, "Squished Waffle")
    }
}
