/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples

import net.ashwork.mc.multilingualexamples.registrar.*
import net.fabricmc.api.ModInitializer

/**
 * The modid of our mod.
 */
const val ID: String = "multilingual_examples"

/**
 * The main mod class. This is where the initialization of the mod happens.
 * The fully qualified name of this class must match that within 'entrypoints.main'.
 * Any entry point must implement [ModInitializer].
 */
internal object MultilingualExamples : ModInitializer {

    /**
     * Initializes the called data once Minecraft is considered to be in a
     * mod-load-ready state.
     */
    override fun onInitialize() {
        // Add registries
        registerGeneral()
        registerBlocks()
        registerItems()
        registerParticleTypes()
    }
}
