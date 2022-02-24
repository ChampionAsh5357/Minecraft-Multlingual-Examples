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

package net.ashwork.multilingualexamples.registrar;

import net.ashwork.multilingualexamples.MultilingualExamples;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * A utility class used to hold and register all particle types for this mod.
 */
public final class ParticleTypeRegistrar {

    /**
     * The registrar used to register the particle types for this mod. Must be attached
     * to the mod event bus through some call within the main mod constructor.
     */
    public static final DeferredRegister<ParticleType<?>> REGISTRAR = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MultilingualExamples.ID);

    public static final RegistryObject<SimpleParticleType> DRIPPING_ASH = REGISTRAR.register("dripping_ash", () -> new SimpleParticleType(false));
}
