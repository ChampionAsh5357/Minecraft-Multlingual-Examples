/*
 * Multilingual Examples
 * Written 2021-2021 by ChampionAsh5357.
 *
 * To the extent possible under law, the author(s) have dedicated all copyright and related
 * and neighboring rights to this software to the public domain worldwide. This software is
 * distributed without any warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication along with this
 * software. If not, see https://creativecommons.org/publicdomain/zero/1.0/.
 */

package net.ashwork.multilingualexamples

import net.ashwork.multilingualexamples.data.ItemModels
import net.ashwork.multilingualexamples.data.Localizations
import net.ashwork.multilingualexamples.registrar.ItemRegistrar
import net.minecraft.data.DataGenerator
import net.minecraftforge.common.data.ExistingFileHelper
import net.minecraftforge.eventbus.api.IEventBus
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
        final IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus()

        // Add registries
        ItemRegistrar.REGISTRAR.register(modBus)

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
