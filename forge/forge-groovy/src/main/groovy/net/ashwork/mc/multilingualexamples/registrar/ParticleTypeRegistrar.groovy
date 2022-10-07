/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.registrar

import net.ashwork.mc.multilingualexamples.MultilingualExamples
import net.minecraft.core.particles.ParticleOptions
import net.minecraft.core.particles.ParticleType
import net.minecraft.core.particles.SimpleParticleType
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject

/**
 * A utility class used to hold and register all particle types for this mod.
 */
final class ParticleTypeRegistrar {

    /**
     * The registrar used to register the particle types for this mod. Must be attached
     * to the mod event bus through some call within the main mod constructor.
     */
    static final DeferredRegister<ParticleType<? extends ParticleOptions>> REGISTRAR = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MultilingualExamples.ID)

    static final RegistryObject<SimpleParticleType> DRIPPING_ASH = REGISTRAR.register('dripping_ash') {
        new SimpleParticleType(false)
    }
}
