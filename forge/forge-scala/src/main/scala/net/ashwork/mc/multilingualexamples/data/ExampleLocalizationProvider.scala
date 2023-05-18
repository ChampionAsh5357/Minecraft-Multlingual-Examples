/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data

import net.ashwork.mc.multilingualexamples.MultilingualExamples
import net.ashwork.mc.multilingualexamples.registrar.{BlockRegistrar, ItemRegistrar}
import net.minecraft.data.PackOutput
import net.minecraftforge.common.data.LanguageProvider

/**
 * A data provider which generates an 'en_us' localization for the mod.
 *
 * @param output the output of the data generator
 */
class ExampleLocalizationProvider(output: PackOutput) extends LanguageProvider(output, MultilingualExamples.ID, "en_us") {

    override def addTranslations(): Unit = {
        // Add pack description
        this.add(s"pack.${MultilingualExamples.ID}.description", "Multilingual Examples Resources")
        
        // Add items
        this.addItem(ItemRegistrar.ASH, "Ash")
        this.addItem(ItemRegistrar.COLLAGE_HELMET, "Hat")
        this.addItem(ItemRegistrar.COLLAGE_CHESTPLATE, "Rocket")
        this.addItem(ItemRegistrar.COLLAGE_LEGGINGS, "Belt")
        this.addItem(ItemRegistrar.COLLAGE_BOOTS, "Ankle Guard")
        this.addItem(ItemRegistrar.WAFFLE_MIX, "Waffle Mix")
        this.addItem(ItemRegistrar.WAFFLE_CONE, "Waffle Cone")
        this.addItem(ItemRegistrar.SNOW_CONE, "Snow Cone")
        this.addItem(ItemRegistrar.ICE_CREAM_SANDWICH, "Ice Cream Sandwich")

        // Add blocks
        this.addBlock(BlockRegistrar.WAFFLE, "Waffle")
        this.addBlock(BlockRegistrar.SQUISHED_WAFFLE, "Squished Waffle")
    }
}
