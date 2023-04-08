/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples;

import net.ashwork.mc.multilingualexamples.registrar.BlockRegistrar;
import net.ashwork.mc.multilingualexamples.registrar.GeneralRegistrar;
import net.ashwork.mc.multilingualexamples.registrar.ItemRegistrar;
import net.ashwork.mc.multilingualexamples.registrar.ParticleTypeRegistrar;
import net.fabricmc.api.ModInitializer;

/**
 * The main mod class. This is where the initialization of the mod happens.
 * The fully qualified name of this class must match that within {@code entrypoints.main}.
 * Any entry point must implement {@link ModInitializer}.
 */
public final class MultilingualExamples implements ModInitializer {

    /**
     * The modid of our mod.
     */
    public static final String ID = "multilingual_examples";

    /**
     * Initializes the called data once Minecraft is considered to be in a
     * mod-load-ready state.
     */
    @Override
    public void onInitialize() {
        // Add registries
        GeneralRegistrar.register();
        BlockRegistrar.register();
        ItemRegistrar.register();
        ParticleTypeRegistrar.register();
    }
}
