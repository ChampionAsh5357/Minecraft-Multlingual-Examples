/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples

import net.ashwork.mc.multilingualexamples.client.MultilingualExamplesClient
import net.ashwork.mc.multilingualexamples.data.*
import net.ashwork.mc.multilingualexamples.data.ExampleItemModelProvider
import net.ashwork.mc.multilingualexamples.data.ExampleLocalizationProvider
import net.ashwork.mc.multilingualexamples.registrar.initRegistrars
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.data.event.GatherDataEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.fml.loading.FMLEnvironment

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
        initRegistrars(modBus)

        // Add client
        if (FMLEnvironment.dist == Dist.CLIENT) MultilingualExamplesClient.init(modBus, forgeBus)

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
            gen.addProvider(true, ExampleBlockStateModelProvider(gen, efh))
            gen.addProvider(true, ExampleItemModelProvider(gen, efh))
            gen.addProvider(true, ExampleLocalizationProvider(gen))
        }

        // Add server providers
        if (event.includeServer()) {
            gen.addProvider(true, ExampleLootTableProvider(gen))
            gen.addProvider(true, ExampleRecipeProvider(gen))
        }
    }
}
