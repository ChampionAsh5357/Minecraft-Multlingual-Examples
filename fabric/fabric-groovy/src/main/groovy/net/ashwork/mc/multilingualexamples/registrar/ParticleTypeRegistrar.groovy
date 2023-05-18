/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.registrar

import net.ashwork.mc.multilingualexamples.MultilingualExamples
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes
import net.minecraft.core.Registry
import net.minecraft.core.particles.ParticleOptions
import net.minecraft.core.particles.ParticleType
import net.minecraft.core.particles.SimpleParticleType
import net.minecraft.resources.ResourceLocation

/**
 * A utility class used to hold and register all particle types for this mod.
 */
final class ParticleTypeRegistrar {

    /**
     * A dummy method used to load the static objects in this class.
     */
    static void registerParticleTypes() {}

    static final SimpleParticleType DRIPPING_ASH = register('dripping_ash', FabricParticleTypes.simple())

    /**
     * Registers an object to the particle types registry.
     *
     * @param name the registry name of the object
     * @param obj the object instance
     * @return the object instance being registered
     */
    private static <T extends ParticleType<? extends ParticleOptions>> T register(final String name, final T obj) {
        return Registry.register(Registry.PARTICLE_TYPE, new ResourceLocation(MultilingualExamples.ID, name), obj)
    }
}
