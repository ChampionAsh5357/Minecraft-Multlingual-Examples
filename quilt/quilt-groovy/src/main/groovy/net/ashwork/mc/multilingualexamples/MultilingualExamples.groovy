/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples

import net.ashwork.mc.multilingualexamples.registrar.ItemRegistrar
import net.ashwork.mc.multilingualexamples.registrar.ParticleTypeRegistrar
import org.quiltmc.loader.api.ModContainer
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer

/**
 * The main mod class. This is where the initialization of the mod happens.
 * The fully qualified name of this class must match that within {@code entrypoints.init}.
 * Any entry point must implement {@link ModInitializer}.
 */
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
     * A list of class loaders used to initialize the registry objects.
     */
    private static final List<Closure<?>> LOADERS = new ArrayList<>()

    /**
     * Initializes the called data once Minecraft is considered to be in a
     * mod-load-ready state.
     */
    @Override
    void onInitialize(final ModContainer mod) {
        // Set modid
        _id = mod.metadata().id()

        // Add registries
        LOADERS.add { { ItemRegistrar.ASH } }
        LOADERS.add { { ParticleTypeRegistrar.DRIPPING_ASH } }
        LOADERS.forEach {
            it.call()
        }
    }
}
