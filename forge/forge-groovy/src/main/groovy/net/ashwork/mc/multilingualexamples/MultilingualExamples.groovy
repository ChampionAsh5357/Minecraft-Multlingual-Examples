/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples

import net.ashwork.mc.multilingualexamples.client.MultilingualExamplesClient
import net.ashwork.mc.multilingualexamples.data.ItemModels
import net.ashwork.mc.multilingualexamples.data.Localizations
import net.ashwork.mc.multilingualexamples.registrar.ItemRegistrar
import net.ashwork.mc.multilingualexamples.registrar.ParticleTypeRegistrar
import net.minecraft.data.DataGenerator
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.data.ExistingFileHelper
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent

import java.util.function.Consumer

/**
 * The main mod class. This is where the initialization of the mod happens.
 * The mod id supplied in the annotation must match that within the {@code mods.toml}.
 */
@Mod(MultilingualExamples.ID)
final class MultilingualExamples {

    /**
     * The modid of our mod.
     */
    static final String ID = 'multilingual_examples'

    /**
     * The mod constructor. All event bus attachments should be present here.
     */
    MultilingualExamples() {
        // Get the event buses
        final IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus(),
                forgeBus = MinecraftForge.EVENT_BUS

        // Add registries
        ItemRegistrar.REGISTRAR.register(modBus)
        ParticleTypeRegistrar.REGISTRAR.register(modBus)

        // Add client
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT) {
            new Runnable() {
                @Override
                void run() {
                    new MultilingualExamplesClient(modBus, forgeBus)
                }
            }
        }

        // Add mod events
        modBus.addListener(new Consumer<GatherDataEvent>() {
            @Override
            void accept(final GatherDataEvent event) {
                attachDataProviders(event)
            }
        })
    }

    /**
     * An event listener that, when fired, attaches the providers to the
     * data generator to generate the associated files.
     *
     * The {@code mod} argument in within {@code minecraft.runs.data} in the
     * buildscript must be equal to {@link #ID}.
     *
     * @param event the {@link GatherDataEvent} event
     */
    private static void attachDataProviders(final GatherDataEvent event) {
        // Grab common data from event
        final DataGenerator gen = event.getGenerator()
        final ExistingFileHelper efh = event.getExistingFileHelper()

        // Add client providers
        if (event.includeClient()) {
            gen.addProvider(new ItemModels(gen, efh))
            gen.addProvider(new Localizations(gen))
        }
    }
}
