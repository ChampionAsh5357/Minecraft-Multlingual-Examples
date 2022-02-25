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

package net.ashwork.multilingualexamples.client

import net.ashwork.multilingualexamples.client.particle.DrippingAshParticle
import net.ashwork.multilingualexamples.registrar.ParticleTypeRegistrar
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
