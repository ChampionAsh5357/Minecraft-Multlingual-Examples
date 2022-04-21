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

package net.ashwork.mc.multilingualexamples

import net.ashwork.mc.multilingualexamples.client.MultilingualExamplesClient
import net.ashwork.mc.multilingualexamples.data.ItemModels
import net.ashwork.mc.multilingualexamples.data.Localizations
import net.ashwork.mc.multilingualexamples.registrar.ITEM_REGISTRAR
import net.ashwork.mc.multilingualexamples.registrar.PARTICLE_TYPE_REGISTRAR
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent

/**
 * The modid of our mod.
 */
const val ID: String = "multilingual_examples"

/**
 * The main mod class. This is where the initialization of the mod happens.
 * The mod id supplied in the annotation must match that within the 'mods.toml'.
 */
@Mod(ID)
internal class MultilingualExamples {

    init {
        // Get the event buses
        val modBus = FMLJavaModLoadingContext.get().modEventBus
        val forgeBus = MinecraftForge.EVENT_BUS

        // Add registries
        ITEM_REGISTRAR.register(modBus)
        PARTICLE_TYPE_REGISTRAR.register(modBus)

        // Add client
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT) {
            Runnable {
                MultilingualExamplesClient(modBus, forgeBus)
            }
        }

        // Add mod events
        modBus.addListener(this::attachDataProviders)
    }

    /**
     * An event listener that, when fired, attaches the providers to the
     * data generator to generate the associated files.
     *
     * The 'mod' argument in within 'minecraft.runs.data' in the
     * buildscript must be equal to [ID].
     *
     * @param event the [GatherDataEvent] event
     */
    private fun attachDataProviders(event: GatherDataEvent) {
        // Grab common data from event
        val gen = event.generator
        val efh = event.existingFileHelper

        // Add client providers
        if (event.includeClient()) {
            gen.addProvider(ItemModels(gen, efh))
            gen.addProvider(Localizations(gen))
        }
    }
}