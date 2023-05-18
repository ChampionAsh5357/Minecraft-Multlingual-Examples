/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples

import net.ashwork.mc.multilingualexamples.registrar.{BlockRegistrar, GeneralRegistrar, ItemRegistrar, ParticleTypeRegistrar}
import org.quiltmc.loader.api.ModContainer
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer

import scala.collection.mutable.ListBuffer

/**
 * The main mod class. This is where the initialization of the mod happens.
 * The fully qualified name of this class must match that within 'entrypoints.init'.
 * Any entry point must implement [[ModInitializer]]
 */
class MultilingualExamples extends ModInitializer {

    /**
     * Initializes the called data once Minecraft is considered to be in a
     * mod-load-ready state.
     */
    override def onInitialize(mod: ModContainer): Unit = {
        // Set modid
        MultilingualExamples._id = mod.metadata().id()

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
    private var _id = ""

    /**
     * Returns the modid of our mod.
     *
     * @return the modid of our mod
     */
    def id(): String = _id
}
