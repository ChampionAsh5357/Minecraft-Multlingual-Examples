/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples

import groovy.transform.CompileStatic
import net.ashwork.mc.multilingualexamples.registrar.BlockRegistrar
import net.ashwork.mc.multilingualexamples.registrar.GeneralRegistrar
import net.ashwork.mc.multilingualexamples.registrar.ItemRegistrar
import net.ashwork.mc.multilingualexamples.registrar.ParticleTypeRegistrar
import org.quiltmc.loader.api.ModContainer
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer

/**
 * The main mod class. This is where the initialization of the mod happens.
 * The fully qualified name of this class must match that within {@code entrypoints.init}.
 * Any entry point must implement {@link ModInitializer}.
 */
@CompileStatic
final class MultilingualExamples implements ModInitializer {

    /**
     * The modid of our mod.
     */
    private static String _id

    /**
     * Returns the modid of our mod.
     *
     * @return the modid of our mod
     */
    static String id() {
        return _id
    }

    /**
     * Initializes the called data once Minecraft is considered to be in a
     * mod-load-ready state.
     */
    @Override
    void onInitialize(final ModContainer mod) {
        // Set modid
        _id = mod.metadata().id()

        // Add registries
        GeneralRegistrar.registerGeneral()
        BlockRegistrar.registerBlocks()
        ItemRegistrar.registerItems()
        ParticleTypeRegistrar.registerParticleTypes()
    }
}
