/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

/**
 * A class instance used to run providers to generate files necessary by specified
 * objects (e.g. models, localizations, etc.) The fully qualified name of this class
 * must match that within 'entrypoints.fabric-datagen'. Any data entry point must
 * implement [DataGeneratorEntrypoint].
 */
internal class MultilingualExamplesData: DataGeneratorEntrypoint {

    override fun onInitializeDataGenerator(gen: FabricDataGenerator) {
        // Add providers
        gen.addProvider(::Localizations)
        gen.addProvider(::Models)
    }
}
