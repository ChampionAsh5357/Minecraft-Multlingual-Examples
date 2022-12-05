/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples

import net.ashwork.mc.multilingualexamples.client.MultilingualExamplesClient
import net.ashwork.mc.multilingualexamples.data.{ExampleBlockStateModelProvider, ExampleItemModelProvider, ExampleLocalizationProvider, ExampleLootTableProvider, ExampleRecipeProvider}
import net.ashwork.mc.multilingualexamples.registrar.{ItemRegistrar, ParticleTypeRegistrar, Registrars}
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.data.event.GatherDataEvent
import net.minecraftforge.fml.loading.FMLEnvironment

/**
 * The main mod class. This is where the initialization of the mod happens.
 * The mod id supplied in the annotation must match that within the 'mods.toml'.
 */
@Mod(MultilingualExamples.ID)
final class MultilingualExamples {

    // Get the event buses
    private final val modBus = FMLJavaModLoadingContext.get.getModEventBus
    private final val forgeBus = MinecraftForge.EVENT_BUS

    // Add registries
    Registrars.init(modBus)

    // Add client
    if (FMLEnvironment.dist == Dist.CLIENT) MultilingualExamplesClient.init(modBus, forgeBus)

    // Add mod events
    modBus.addListener(this.attachDataProviders)

    /**
     * An event listener that, when fired, attaches the providers to the
     * data generator to generate the associated files.
     *
     * The 'mod' argument in within 'minecraft.runs.data' in the
     * buildscript must be equal to [[MultilingualExamples.ID]].
     *
     * @param event the [[GatherDataEvent]] event
     */
    private def attachDataProviders(event: GatherDataEvent): Unit = {
        // Grab common data from event
        val gen = event.getGenerator
        val efh = event.getExistingFileHelper

        // Add client providers
        if (event.includeClient) {
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

/**
 * The global instance of our main mod class. Used to store any constants.
 */
object MultilingualExamples {
    /**
     * The modid of our mod.
     */
    final val ID = "multilingual_examples"
}
