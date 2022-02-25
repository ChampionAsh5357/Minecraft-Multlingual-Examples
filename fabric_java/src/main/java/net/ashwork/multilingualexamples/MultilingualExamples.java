/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357.
 *
 * To the extent possible under law, the author(s) have dedicated all copyright and related
 * and neighboring rights to this software to the public domain worldwide. This software is
 * distributed without any warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication along with this
 * software. If not, see https://creativecommons.org/publicdomain/zero/1.0/.
 */

package net.ashwork.multilingualexamples;

import net.ashwork.multilingualexamples.registrar.ItemRegistrar;
import net.ashwork.multilingualexamples.registrar.ParticleTypeRegistrar;
import net.fabricmc.api.ModInitializer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * The main mod class. This is where the initialization of the mod happens.
 * The fully qualified name of this class must match that within {@code entrypoints.main}.
 * Any entry point must implement {@link ModInitializer}.
 */
public final class MultilingualExamples implements ModInitializer {

    /**
     * The modid of our mod.
     */
    public static final String ID = "multilingual_examples";

    /**
     * A list of class loaders used to initialize the registry objects.
     */
    private static final List<Supplier<?>> LOADERS = new ArrayList<>();

    /**
     * Initializes the called data once Minecraft is considered to be in a
     * mod-load-ready state.
     */
    @Override
    public void onInitialize() {
        // Add registries
        LOADERS.add(() -> ItemRegistrar.ASH);
        LOADERS.add(() -> ParticleTypeRegistrar.DRIPPING_ASH);
        LOADERS.forEach(Supplier::get);
    }
}
