/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples;

import net.ashwork.mc.multilingualexamples.client.MultilingualExamplesClient;
import net.ashwork.mc.multilingualexamples.data.ExampleBlockStateModelProvider;
import net.ashwork.mc.multilingualexamples.data.ExampleItemModelProvider;
import net.ashwork.mc.multilingualexamples.data.ExampleLocalizationProvider;
import net.ashwork.mc.multilingualexamples.data.ExampleLootTableProvider;
import net.ashwork.mc.multilingualexamples.data.ExampleRecipeProvider;
import net.ashwork.mc.multilingualexamples.registrar.Registrars;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * The main mod class. This is where the initialization of the mod happens.
 * The mod id supplied in the annotation must match that within the {@code mods.toml}.
 */
@Mod(MultilingualExamples.ID)
public final class MultilingualExamples {

    /**
     * The modid of our mod.
     */
    public static final String ID = "multilingual_examples";

    /**
     * The mod constructor. All event bus attachments should be present here.
     */
    public MultilingualExamples() {
        // Get the event buses
        final IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus(),
                forgeBus = MinecraftForge.EVENT_BUS;

        // Add registries
        Registrars.init(modBus);

        // Add client
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> new MultilingualExamplesClient(modBus, forgeBus));

        // Add mod events
        modBus.addListener(this::attachDataProviders);
    }

    /**
     * An event listener that, when fired, attaches the providers to the
     * data generator to generate the associated files.
     *
     * @implSpec
     * The {@code mod} argument in within {@code minecraft.runs.data} in the
     * buildscript must be equal to {@link #ID}.
     *
     * @param event the {@link GatherDataEvent} event
     */
    private void attachDataProviders(final GatherDataEvent event) {
        // Grab common data from event
        final DataGenerator gen = event.getGenerator();
        final ExistingFileHelper efh = event.getExistingFileHelper();

        // Add client providers
        if (event.includeClient()) {
            gen.addProvider(true, new ExampleBlockStateModelProvider(gen, efh));
            gen.addProvider(true, new ExampleItemModelProvider(gen, efh));
            gen.addProvider(true, new ExampleLocalizationProvider(gen));
        }

        // Add server providers
        if (event.includeServer()) {
            gen.addProvider(true, new ExampleLootTableProvider(gen));
            gen.addProvider(true, new ExampleRecipeProvider(gen));
        }
    }
}
