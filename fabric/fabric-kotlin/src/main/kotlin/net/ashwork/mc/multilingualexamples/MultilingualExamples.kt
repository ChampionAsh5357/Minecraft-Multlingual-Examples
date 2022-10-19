/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples

import net.ashwork.mc.multilingualexamples.registrar.ASH
import net.ashwork.mc.multilingualexamples.registrar.DRIPPING_ASH
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
     * A list of class loaders used to initialize the registry objects.
     */
    private val LOADERS: MutableList<() -> Any> = mutableListOf()

    /**
     * Initializes the called data once Minecraft is considered to be in a
     * mod-load-ready state.
     */
    override fun onInitialize() {
        // Add registries
        LOADERS.add { ASH }
        LOADERS.add { DRIPPING_ASH }
        LOADERS.forEach { it.invoke() }
    }
}
