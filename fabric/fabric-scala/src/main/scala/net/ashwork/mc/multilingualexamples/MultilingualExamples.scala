/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples

import net.ashwork.mc.multilingualexamples.registrar.{BlockRegistrar, GeneralRegistrar, ItemRegistrar, ParticleTypeRegistrar}
import net.fabricmc.api.ModInitializer

import scala.collection.mutable.ListBuffer

/**
 * The main mod class. This is where the initialization of the mod happens.
 * The fully qualified name of this class must match that within 'entrypoints.main'.
 * Any entry point must implement [[ModInitializer]]
 */
class MultilingualExamples extends ModInitializer {
    /**
     * Initializes the called data once Minecraft is considered to be in a
     * mod-load-ready state.
     */
    override def onInitialize(): Unit = {
        // Add registries
        GeneralRegistrar.register()
        BlockRegistrar.register()
        ItemRegistrar.register()
        ParticleTypeRegistrar.register()
    }
}
object MultilingualExamples {
    /**
     * The modid of our mod.
     */
    final val ID = "multilingual_examples"
}
