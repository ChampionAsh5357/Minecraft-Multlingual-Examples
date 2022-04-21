/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.client

import net.ashwork.mc.multilingualexamples.client.particle.DrippingAshParticle
import net.ashwork.mc.multilingualexamples.registrar.ParticleTypeRegistrar
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.particle.v1.FabricSpriteProvider
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry
import net.minecraft.client.particle.ParticleProvider
import net.minecraft.core.particles.SimpleParticleType

/**
 * An isolated class for initialization of anything the mod needs specifically
 * for the client. The fully qualified name of this class must match that within
 * {@code entrypoints.client}. Any client entry point must implement {@link ClientModInitializer}.
 */
class MultilingualExamplesClient implements ClientModInitializer {

    @Override
    void onInitializeClient() {
        /*
         * Register our particle factory.
         *
         * Since this is using net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry.PendingParticleFactory, this will need
         * a JSON to reference the particle sprites. This will use the registry name of the particle type itself and be
         * located within the 'particles' directory within assets.
         *
         * Textures referenced in the JSON will be in the 'particle' directory within textures.
         */
        ParticleFactoryRegistry.getInstance().register(ParticleTypeRegistrar.DRIPPING_ASH, new ParticleFactoryRegistry.PendingParticleFactory<SimpleParticleType>() {
            @Override
            ParticleProvider<SimpleParticleType> create(FabricSpriteProvider sprites) {
                return new DrippingAshParticle.DrippingAshParticleProvider(sprites)
            }
        })
    }
}
