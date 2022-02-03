/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357.
 *
 * To the extent possible under law, the author(s) have dedicated all copyright and related
 * and neighboring rights to this software to the public domain worldwide. This software is
 * distributed without any warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication along with this
 * software. If not, see https://creativecommons.org/publicdomain/zero/1.0/.
 */

package net.ashwork.multilingualexamples

import net.ashwork.multilingualexamples.registrar.ItemRegistrar
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
        LOADERS.foreach(it => it.apply())
    }
}