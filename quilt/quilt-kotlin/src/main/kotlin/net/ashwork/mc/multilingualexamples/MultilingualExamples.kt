/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples

import net.ashwork.mc.multilingualexamples.registrar.*
import org.quiltmc.loader.api.ModContainer
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer

/**
 * The modid of our mod.
 */
private var id: String = ""

/**
 * Returns the modid of our mod.
 *
 * @return the modid of our mod
 */
fun id(): String = id

/**
 * The main mod class. This is where the initialization of the mod happens.
 * The fully qualified name of this class must match that within 'entrypoints.init'.
 * Any entry point must implement [ModInitializer].
 */
internal class MultilingualExamples : ModInitializer {

    /**
     * Initializes the called data once Minecraft is considered to be in a
     * mod-load-ready state.
     */
    override fun onInitialize(mod: ModContainer) {
        // Set modid
        id = mod.metadata().id()

        // Add registries
        registerGeneral()
        registerBlocks()
        registerItems()
        registerParticleTypes()
    }
}
