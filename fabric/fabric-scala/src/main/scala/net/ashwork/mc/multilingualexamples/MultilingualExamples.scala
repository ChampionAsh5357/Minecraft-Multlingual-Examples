/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples

import net.ashwork.mc.multilingualexamples.registrar.{ItemRegistrar, ParticleTypeRegistrar}
import net.fabricmc.api.ModInitializer

import scala.collection.mutable.ListBuffer

/**
 * The main mod class. This is where the initialization of the mod happens.
 * The fully qualified name of this class must match that within 'entrypoints.main'.
 * Any entry point must implement [[ModInitializer]]
 */
object MultilingualExamples extends ModInitializer {

    /**
     * The modid of our mod.
     */
    final val ID = "multilingual_examples"

    /**
     * A list of class loaders used to initialize the registry objects.
     */
    private final val LOADERS: ListBuffer[() => Unit] = ListBuffer.empty

    /**
     * Initializes the called data once Minecraft is considered to be in a
     * mod-load-ready state.
     */
    override def onInitialize(): Unit = {
        // Add registries
        LOADERS.addOne(() => ItemRegistrar.ASH)
        LOADERS.addOne(() => ParticleTypeRegistrar.DRIPPING_ASH)
        LOADERS.foreach(it => it.apply())
    }
}