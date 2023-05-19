/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples

import net.ashwork.mc.multilingualexamples.client.MultilingualExamplesClient
import net.ashwork.mc.multilingualexamples.data.*
import net.ashwork.mc.multilingualexamples.data.loot.ExampleBlockLootSubProvider
import net.ashwork.mc.multilingualexamples.registrar.initRegistrars
import net.minecraft.DetectedVersion
import net.minecraft.data.DataGenerator
import net.minecraft.data.DataProvider
import net.minecraft.data.DataProvider.Factory
import net.minecraft.data.loot.LootTableProvider
import net.minecraft.data.metadata.PackMetadataGenerator
import net.minecraft.network.chat.Component
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.metadata.pack.PackMetadataSection
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
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

        // Add pack.mcmeta provider
        addProvider(gen) { output ->
            PackMetadataGenerator(output).add(PackMetadataSection.TYPE, PackMetadataSection(
                Component.translatable("pack.$ID.description"),
                PackType.CLIENT_RESOURCES.getVersion(DetectedVersion.BUILT_IN),
                PackType.values().associateWith { it.getVersion(DetectedVersion.BUILT_IN) }
            ))
        }

        // Add client providers
        if (event.includeClient()) {
            addProvider(gen) { ExampleBlockStateModelProvider(it, efh) }
            addProvider(gen) { ExampleItemModelProvider(it, efh) }
            addProvider(gen, ::ExampleLocalizationProvider)
        }

        // Add server providers
        if (event.includeServer()) {
            addProvider(gen) {
                LootTableProvider(it, emptySet(), listOf(
                    LootTableProvider.SubProviderEntry(::ExampleBlockLootSubProvider, LootContextParamSets.BLOCK)
                ))
            }
            addProvider(gen, ::ExampleRecipeProvider)
        }
    }
}

/**
 * A helper method for registering a factory without encountering an ambiguous lambda
 * compile error.
 *
 * @param generator the generator to add the provider to
 * @param factory the factory to construct the provider
 * @param T the type of the provider
 */
private fun <T: DataProvider> addProvider(generator: DataGenerator, factory: Factory<T>) =
    generator.addProvider(true, factory)
