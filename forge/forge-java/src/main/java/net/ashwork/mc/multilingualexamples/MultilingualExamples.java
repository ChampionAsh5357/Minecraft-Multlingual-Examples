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
import net.ashwork.mc.multilingualexamples.data.ExampleRecipeProvider;
import net.ashwork.mc.multilingualexamples.data.loot.ExampleBlockLootSubProvider;
import net.ashwork.mc.multilingualexamples.registrar.Registrars;
import net.minecraft.DetectedVersion;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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

        // Add pack.mcmeta provider
        addProvider(gen, output -> new PackMetadataGenerator(output)
                .add(PackMetadataSection.TYPE, new PackMetadataSection(
                        Component.translatable("pack." + MultilingualExamples.ID + ".description"),
                        PackType.CLIENT_RESOURCES.getVersion(DetectedVersion.BUILT_IN),
                        Arrays.stream(PackType.values()).collect(Collectors.toMap(Function.identity(), type -> type.getVersion(DetectedVersion.BUILT_IN)))
                ))
        );

        // Add client providers
        if (event.includeClient()) {
            addProvider(gen, output -> new ExampleBlockStateModelProvider(output, efh));
            addProvider(gen, output -> new ExampleItemModelProvider(output, efh));
            addProvider(gen, ExampleLocalizationProvider::new);
        }

        // Add server providers
        if (event.includeServer()) {
            addProvider(gen, output -> new LootTableProvider(output, Collections.emptySet(), List.of(
                    new LootTableProvider.SubProviderEntry(ExampleBlockLootSubProvider::new, LootContextParamSets.BLOCK)
            )));
            addProvider(gen, ExampleRecipeProvider::new);
        }
    }

    /**
     * A helper method for registering a factory without encountering an ambiguous lambda
     * compile error.
     *
     * @param generator the generator to add the provider to
     * @param factory the factory to construct the provider
     * @param <T> the type of the provider
     */
    private static <T extends DataProvider> void addProvider(DataGenerator generator, DataProvider.Factory<T> factory) {
        generator.addProvider(true, factory);
    }
}
