/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples

import net.ashwork.mc.multilingualexamples.client.MultilingualExamplesClient
import net.ashwork.mc.multilingualexamples.data.{ItemModels, Localizations}
import net.ashwork.mc.multilingualexamples.registrar.{ItemRegistrar, ParticleTypeRegistrar}
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.data.event.GatherDataEvent

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
    ItemRegistrar.REGISTRAR.register(modBus)
    ParticleTypeRegistrar.REGISTRAR.register(modBus)

    // Add client
    DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () => () => MultilingualExamplesClient.init(modBus, forgeBus))

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
            gen.addProvider(true, ItemModels(gen, efh))
            gen.addProvider(true, Localizations(gen))
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
