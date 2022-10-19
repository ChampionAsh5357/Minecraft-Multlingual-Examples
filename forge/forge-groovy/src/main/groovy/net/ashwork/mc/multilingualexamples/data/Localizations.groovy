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
 * A data provider which generates an {@code en_us} localization for the mod.
 */
final class Localizations extends LanguageProvider {

    /**
     * A simple constructor.
     *
     * @param gen the generator being written to
     */
    Localizations(final DataGenerator gen) {
        super(gen, MultilingualExamples.ID, "en_us")
    }

    @Override
    protected void addTranslations() {
        // Add items
        this.addItem(ItemRegistrar.ASH, "Ash")
        this.addItem(ItemRegistrar.COLLAGE_HELMET, "Hat")
        this.addItem(ItemRegistrar.COLLAGE_CHESTPLATE, "Rocket")
        this.addItem(ItemRegistrar.COLLAGE_LEGGINGS, "Belt")
        this.addItem(ItemRegistrar.COLLAGE_BOOTS, "Ankle Guard")
    }
}
