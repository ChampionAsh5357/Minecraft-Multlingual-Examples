/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data

import net.ashwork.mc.multilingualexamples.MultilingualExamples
import net.ashwork.mc.multilingualexamples.registrar.BlockRegistrar
import net.ashwork.mc.multilingualexamples.registrar.ItemRegistrar
import net.minecraft.data.DataGenerator
import net.minecraftforge.common.data.LanguageProvider

/**
 * A data provider which generates an {@code en_us} localization for the mod.
 */
final class ExampleLocalizationProvider extends LanguageProvider {

    /**
     * A simple constructor.
     *
     * @param gen the generator being written to
     */
    ExampleLocalizationProvider(final DataGenerator gen) {
        super(gen, MultilingualExamples.ID, 'en_us')
    }

    @Override
    protected void addTranslations() {
        // Add items
        this.addItem(ItemRegistrar.ASH, 'Ash')
        this.addItem(ItemRegistrar.COLLAGE_HELMET, 'Hat')
        this.addItem(ItemRegistrar.COLLAGE_CHESTPLATE, 'Rocket')
        this.addItem(ItemRegistrar.COLLAGE_LEGGINGS, 'Belt')
        this.addItem(ItemRegistrar.COLLAGE_BOOTS, 'Ankle Guard')
        this.addItem(ItemRegistrar.WAFFLE_MIX, 'Waffle Mix')
        this.addItem(ItemRegistrar.WAFFLE_CONE, 'Waffle Cone')
        this.addItem(ItemRegistrar.SNOW_CONE, 'Snow Cone')
        this.addItem(ItemRegistrar.ICE_CREAM_SANDWICH, 'Ice Cream Sandwich')

        // Add blocks
        this.addBlock(BlockRegistrar.WAFFLE, 'Waffle')
        this.addBlock(BlockRegistrar.SQUISHED_WAFFLE, 'Squished Waffle')
    }
}
