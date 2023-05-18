/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.registrar

import net.minecraft.core.particles.SimpleParticleType
import net.minecraftforge.registries.RegistryObject

/*
 * A utility file used to hold and register all particle types for this mod.
 */

/**
 * A dummy method used to load the static objects in this class.
 */
internal fun registerParticleTypes() = run { }

val DRIPPING_ASH: RegistryObject<SimpleParticleType> = PARTICLE_TYPE_REGISTRAR.register("dripping_ash") {
    SimpleParticleType(false)
}
