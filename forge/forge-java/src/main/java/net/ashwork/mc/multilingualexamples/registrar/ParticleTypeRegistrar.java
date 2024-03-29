/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.registrar;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.RegistryObject;

/**
 * A utility class used to hold and register all particle types for this mod.
 */
public final class ParticleTypeRegistrar {

    /**
     * A dummy method used to load the static objects in this class.
     */
    public static void register() {}

    public static final RegistryObject<SimpleParticleType> DRIPPING_ASH = Registrars.PARTICLE_TYPES.register("dripping_ash", () -> new SimpleParticleType(false));
}
