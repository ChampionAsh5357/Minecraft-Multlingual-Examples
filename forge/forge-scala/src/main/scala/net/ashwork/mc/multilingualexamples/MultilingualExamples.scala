/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples

import net.ashwork.mc.multilingualexamples.MultilingualExamples.addProvider
import net.ashwork.mc.multilingualexamples.client.MultilingualExamplesClient
import net.ashwork.mc.multilingualexamples.data.{ExampleBlockStateModelProvider, ExampleItemModelProvider, ExampleLocalizationProvider, ExampleRecipeProvider}
import net.ashwork.mc.multilingualexamples.data.loot.ExampleBlockLootSubProvider
import net.ashwork.mc.multilingualexamples.registrar.{ItemRegistrar, ParticleTypeRegistrar, Registrars}
import net.minecraft.DetectedVersion
import net.minecraft.data.DataProvider.Factory
import net.minecraft.data.loot.LootTableProvider
import net.minecraft.data.loot.LootTableProvider.SubProviderEntry
import net.minecraft.data.metadata.PackMetadataGenerator
import net.minecraft.data.{DataGenerator, DataProvider}
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.metadata.pack.PackMetadataSection
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.data.event.GatherDataEvent
import net.minecraftforge.fml.loading.FMLEnvironment

import scala.collection.mutable.ListBuffer
import scala.jdk.CollectionConverters.*
import scala.jdk.FunctionConverters.*

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

        // Add pack.mcmeta provider
        addProvider(gen, PackMetadataGenerator(_).add(PackMetadataSection.TYPE, PackMetadataSection(
            Component.translatable(s"pack.${MultilingualExamples.ID}.description"),
            PackType.CLIENT_RESOURCES.getVersion(DetectedVersion.BUILT_IN),
            PackType.values.map(`type` => (`type`, Integer.valueOf(`type`.getVersion(DetectedVersion.BUILT_IN)))).toMap.asJava
        )))

        // Add client providers
        if (event.includeClient) {
            addProvider(gen, ExampleBlockStateModelProvider(_, efh))
            addProvider(gen, ExampleItemModelProvider(_, efh))
            addProvider(gen, ExampleLocalizationProvider(_))
        }

        // Add server providers
        if (event.includeServer()) {
            addProvider(gen, LootTableProvider(_, Set.empty[ResourceLocation].asJava, List(
                SubProviderEntry((() => ExampleBlockLootSubProvider()).asJavaSupplier, LootContextParamSets.BLOCK)
            ).asJava))
            addProvider(gen, ExampleRecipeProvider(_))
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

    /**
     * A helper method for registering a factory without encountering an ambiguous lambda
     * compile error.
     *
     * @param generator the generator to add the provider to
     * @param factory   the factory to construct the provider
     * @tparam T        the type of the provider
     */
    private def addProvider[T <: DataProvider](generator: DataGenerator, factory: Factory[T]): Unit =
        generator.addProvider(true, factory)
}
