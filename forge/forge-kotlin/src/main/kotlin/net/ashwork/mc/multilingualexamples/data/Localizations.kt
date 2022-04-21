/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data

import net.ashwork.mc.multilingualexamples.ID
import net.ashwork.mc.multilingualexamples.registrar.ASH
import net.minecraft.data.DataGenerator
import net.minecraftforge.common.data.LanguageProvider

/**
 * A data provider which generates an 'en_us' localization for the mod.
 *
 * @param gen the generator being written to
 */
internal class Localizations(gen: DataGenerator) : LanguageProvider(gen, ID, "en_us") {

    override fun addTranslations() {
        // Add items
        this.addItem(ASH, "Ash")
    }
}