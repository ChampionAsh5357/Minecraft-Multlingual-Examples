/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data

import net.ashwork.mc.multilingualexamples.registrar.ItemRegistrar
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider

/**
 * A data provider which generates an {@code en_us} localization for the mod.
 */
final class Localizations extends FabricLanguageProvider {

    /**
     * A simple constructor.
     *
     * @param gen the generator being written to
     */
    Localizations(FabricDataGenerator gen) {
        super(gen)
    }

    @Override
    void generateTranslations(TranslationBuilder builder) {
        // Add items
        builder.add(ItemRegistrar.ASH, "Ash")
        builder.add(ItemRegistrar.COLLAGE_HELMET, "Hat")
        builder.add(ItemRegistrar.COLLAGE_CHESTPLATE, "Rocket")
        builder.add(ItemRegistrar.COLLAGE_LEGGINGS, "Belt")
        builder.add(ItemRegistrar.COLLAGE_BOOTS, "Ankle Guard")
    }
}
