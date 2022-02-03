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

import net.ashwork.multilingualexamples.data.{ItemModels, Localizations}
import net.ashwork.multilingualexamples.registrar.ItemRegistrar
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent

/**
 * The main mod class. This is where the initialization of the mod happens.
 * The mod id supplied in the annotation must match that within the 'mods.toml'.
 */
@Mod(MultilingualExamples.ID)
final class MultilingualExamples {

    // Get the event buses
    private final val modBus = FMLJavaModLoadingContext.get.getModEventBus

    // Add registries
    ItemRegistrar.REGISTRAR.register(modBus)

    // Add mod events
    modBus.addListener(this.attachDataProviders)

    private def attachDataProviders(event: GatherDataEvent): Unit = {
        // Grab common data from event
        val gen = event.getGenerator
        val efh = event.getExistingFileHelper

        // Add client providers
        if (event.includeClient) {
            gen.addProvider(ItemModels(gen, efh))
            gen.addProvider(Localizations(gen))
        }
    }
}

/**
 * The global instance of our main mod class. Used to store any constants.
 */
object MultilingualExamples {
    /**
     * The modid of our mod.
     */
    final val ID = "multilingual_examples"
}
