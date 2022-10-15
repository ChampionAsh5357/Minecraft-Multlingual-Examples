/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data

import net.ashwork.mc.multilingualexamples.MultilingualExamples
import net.ashwork.mc.multilingualexamples.registrar.ItemRegistrar
import net.minecraft.data.DataGenerator
import net.minecraftforge.common.data.LanguageProvider

/**
 * A data provider which generates an 'en_us' localization for the mod.
 *
 * @param gen the generator being written to
 */
class Localizations(gen: DataGenerator) extends LanguageProvider(gen, MultilingualExamples.ID, "en_us") {

    override def addTranslations(): Unit = {
        // Add items
        this.addItem(ItemRegistrar.ASH, "Ash")
    }
}

/**
 * The global instance of [[Localizations]]. Created for apply factories.
 */
object Localizations {

    /**
     * A simple constructor.
     *
     * @param gen the generator being written to
     * @return a new [[Localizations]] instance
     */
    def apply(gen: DataGenerator): Localizations = new Localizations(gen)
}
