/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data

import net.ashwork.mc.multilingualexamples.data.loot.ExampleBlockLootTableProvider
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
/**
 * A class instance used to run providers to generate files necessary by specified
 * objects (e.g. models, localizations, etc.) The fully qualified name of this class
 * must match that within {@code entrypoints.fabric-datagen}. Any data entry point
 * must implement {@link net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint}.
 */
final class MultilingualExamplesData implements DataGeneratorEntrypoint {

    @Override
    void onInitializeDataGenerator(FabricDataGenerator gen) {
        // Add providers
        gen.addProvider { new ExampleLocalizationProvider(it) }
        gen.addProvider { new ExampleModelProvider(it) }

        gen.addProvider { new ExampleBlockLootTableProvider(it) }
        gen.addProvider { new ExampleRecipeProvider(it) }
    }
}
