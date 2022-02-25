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

package net.ashwork.multilingualexamples.registrar

import net.ashwork.multilingualexamples.ID
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