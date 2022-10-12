/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data

import net.ashwork.mc.multilingualexamples.registrar.ItemRegistrar
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider

/**
 * A data provider which generates an 'en_us' localization for the mod.
 *
 * @param gen the generator being written to
 */
class Localizations(gen: FabricDataGenerator) extends FabricLanguageProvider(gen) {

    override def generateTranslations(builder: FabricLanguageProvider.TranslationBuilder): Unit = {
        // Add items
        builder.add(ItemRegistrar.ASH, "Ash")
    }
}
