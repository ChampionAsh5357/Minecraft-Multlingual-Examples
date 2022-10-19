/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.registrar

import net.ashwork.mc.multilingualexamples.MultilingualExamples
import net.minecraft.core.particles.SimpleParticleType
import net.minecraftforge.registries.{DeferredRegister, ForgeRegistries}

/**
 * A utility class used to hold and register all particle types for this mod.
 */
object ParticleTypeRegistrar {

    /**
     * The registrar used to register the particle types for this mod. Must be attached
     * to the mod event bus through some call within the main mod constructor.
     */
    final val REGISTRAR = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MultilingualExamples.ID)

    final val DRIPPING_ASH = REGISTRAR.register("dripping_ash", () => SimpleParticleType(false))
}
