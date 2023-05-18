/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.registrar

import net.ashwork.mc.multilingualexamples.id
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes
import net.minecraft.core.Registry
import net.minecraft.core.particles.ParticleOptions
import net.minecraft.core.particles.ParticleType
import net.minecraft.core.particles.SimpleParticleType
import net.minecraft.resources.ResourceLocation

/**
 * A dummy method used to load the static objects in this class.
 */
fun registerParticleTypes() {}

val DRIPPING_ASH: SimpleParticleType = register("dripping_ash", FabricParticleTypes.simple())

/**
 * Registers an object to the particle type registry.
 *
 * @param name the registry name of the object
 * @param obj the object instance
 * @param T the type of the object
 * @return the object instance being registered
 */
private fun <T: ParticleType<out ParticleOptions>> register(name: String, obj: T): T =
    Registry.register(Registry.PARTICLE_TYPE, ResourceLocation(id(), name), obj)
