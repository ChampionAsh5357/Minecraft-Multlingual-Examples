/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.registrar

import net.ashwork.mc.multilingualexamples.MultilingualExamples
import net.minecraft.core.particles.SimpleParticleType
import net.minecraftforge.registries.{DeferredRegister, ForgeRegistries}

/**
 * A utility object used to hold and register all particle types for this mod.
 */
object ParticleTypeRegistrar {

    /**
     * A dummy method used to load the static objects in this class.
     */
    def register(): Unit = {}

    final val DRIPPING_ASH = Registrars.PARTICLE_TYPE_REGISTRAR.register("dripping_ash", () => SimpleParticleType(false))
}
