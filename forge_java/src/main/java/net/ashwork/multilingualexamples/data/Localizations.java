/*
 * Multilingual Examples
 * Written 2021-2021 by ChampionAsh5357.
 *
 * To the extent possible under law, the author(s) have dedicated all copyright and related
 * and neighboring rights to this software to the public domain worldwide. This software is
 * distributed without any warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication along with this
 * software. If not, see https://creativecommons.org/publicdomain/zero/1.0/.
 */

package net.ashwork.multilingualexamples.data;

import net.ashwork.multilingualexamples.MultilingualExamples;
import net.ashwork.multilingualexamples.registrar.ItemRegistrar;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

/**
 * A data provider which generates an {@code en_us} localization for the mod.
 */
public final class Localizations extends LanguageProvider {

    /**
     * A simple constructor.
     *
     * @param gen the generator being written to
     */
    public Localizations(final DataGenerator gen) {
        super(gen, MultilingualExamples.ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        // Add items
        this.addItem(ItemRegistrar.ASH, "Ash");
    }
}
