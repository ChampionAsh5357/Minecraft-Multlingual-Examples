/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples

import groovy.transform.CompileStatic
import net.ashwork.mc.multilingualexamples.client.MultilingualExamplesClient
import net.ashwork.mc.multilingualexamples.data.*
import net.ashwork.mc.multilingualexamples.registrar.Registrars
import net.minecraft.data.DataGenerator
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.data.ExistingFileHelper
import net.minecraftforge.data.event.GatherDataEvent
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.fml.loading.FMLEnvironment
/**
 * The main mod class. This is where the initialization of the mod happens.
 * The mod id supplied in the annotation must match that within the {@code mods.toml}.
 */
@CompileStatic
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
        Registrars.init(modBus)

        // Add client
        if (FMLEnvironment.dist === Dist.CLIENT) new MultilingualExamplesClient(modBus, forgeBus)

        modBus.addListener(EventPriority.NORMAL, false, GatherDataEvent, this::attachDataProviders)
    }

    /**
     * Prefixes a path if it has not already been prefixed.
     *
     * @param loc the name being prefixed
     * @param prefix the prefix to attach to the path
     * @return a prefixed {@link ResourceLocation}
     */
    static ResourceLocation prefixPath(ResourceLocation loc, String prefix) {
        loc.getPath().contains('/') ? loc : new ResourceLocation(loc.getNamespace(), "$prefix/${loc.getPath()}")
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
            gen.addProvider(true, new ExampleBlockStateModelProvider(gen, efh))
            gen.addProvider(true, new ExampleItemModelProvider(gen, efh))
            gen.addProvider(true, new ExampleLocalizationProvider(gen))
        }

        // Add server providers
        if (event.includeServer()) {
            gen.addProvider(true, new ExampleLootTableProvider(gen))
            gen.addProvider(true, new ExampleRecipeProvider(gen))
        }
    }
}
