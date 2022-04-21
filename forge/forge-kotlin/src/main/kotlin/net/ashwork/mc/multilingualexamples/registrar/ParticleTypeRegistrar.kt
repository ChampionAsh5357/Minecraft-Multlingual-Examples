/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.registrar

import net.ashwork.mc.multilingualexamples.ID
import net.minecraft.core.particles.SimpleParticleType
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject

/**
 * The registrar used to register the particle types for this mod. Must be attached
 * to the mod event bus through some call within the main mod constructor.
 */
internal val PARTICLE_TYPE_REGISTRAR = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ID)

val DRIPPING_ASH: RegistryObject<SimpleParticleType> = PARTICLE_TYPE_REGISTRAR.register("dripping_ash") {
    SimpleParticleType(false)
}