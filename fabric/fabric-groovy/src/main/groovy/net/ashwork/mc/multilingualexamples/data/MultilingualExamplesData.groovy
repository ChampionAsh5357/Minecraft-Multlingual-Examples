/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data

import groovy.transform.CompileStatic
import net.ashwork.mc.multilingualexamples.data.loot.ExampleBlockLootTableProvider
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator.Pack
import net.minecraft.data.DataProvider

/**
 * A class instance used to run providers to generate files necessary by specified
 * objects (e.g. models, localizations, etc.) The fully qualified name of this class
 * must match that within {@code entrypoints.fabric-datagen}. Any data entry point
 * must implement {@link net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint}.
 */
@CompileStatic
final class MultilingualExamplesData implements DataGeneratorEntrypoint {

    @Override
    void onInitializeDataGenerator(FabricDataGenerator gen) {
        var pack = gen.createPack()

        // Add providers
        addProvider(pack) { new ExampleLocalizationProvider(it) }
        addProvider(pack) { new ExampleModelProvider(it) }

        addProvider(pack) { new ExampleBlockLootTableProvider(it) }
        addProvider(pack) { new ExampleRecipeProvider(it) }
    }

    /**
     * A helper method for registering a factory without encountering an ambiguous lambda
     * compile error.
     *
     * @param pack the pack to add the provider to
     * @param factory the factory to construct the provider
     * @param T the type of the provider
     */
    private static <T extends DataProvider> void addProvider(Pack pack, Pack.Factory<T> factory) {
        pack.addProvider(factory)
    }
}
