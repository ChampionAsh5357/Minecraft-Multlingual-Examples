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

package net.ashwork.multilingualexamples

import net.ashwork.multilingualexamples.registrar.ASH
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
        LOADERS.forEach { it.invoke() }
    }
}