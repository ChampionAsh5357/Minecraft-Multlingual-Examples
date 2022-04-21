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

package net.ashwork.mc.multilingualexamples.client

import net.ashwork.mc.multilingualexamples.client.particle.DrippingAshParticle
import net.ashwork.mc.multilingualexamples.registrar.ParticleTypeRegistrar
import net.minecraft.client.Minecraft
import net.minecraft.client.particle.ParticleEngine
import net.minecraft.client.particle.ParticleProvider
import net.minecraft.client.particle.SpriteSet
import net.minecraft.core.particles.SimpleParticleType
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent
import net.minecraftforge.eventbus.api.IEventBus

import java.util.function.Consumer
/**
 * An isolated class for initialization of anything the mod needs specifically
 * for the client. This should only be referenced through a sided check.
 */
class MultilingualExamplesClient {

    /**
     * Default constructor.
     *
     * @param modBus the mod's event bus
     * @param forgeBus the forge event bus
     */
    MultilingualExamplesClient(final IEventBus modBus, final IEventBus forgeBus) {
        modBus.addListener(new Consumer<ParticleFactoryRegisterEvent>() {
            @Override
            void accept(final ParticleFactoryRegisterEvent event) {
                onRegisterParticleFactories(event)
            }
        })
    }

    /**
     * An event used to register particle factories.
     *
     * @param event an event instance
     */
    private static void onRegisterParticleFactories(final ParticleFactoryRegisterEvent event) {
        /*
         * Register our particle factory.
         *
         * Since this is using net.minecraft.client.particle.ParticleEngine.SpriteParticleRegistration, this will need
         * a JSON to reference the particle sprites. This will use the registry name of the particle type itself and be
         * located within the 'particles' directory within assets.
         *
         * Textures referenced in the JSON will be in the 'particle' directory within textures.
         */
        Minecraft.getInstance().particleEngine.register(ParticleTypeRegistrar.DRIPPING_ASH.get(), new ParticleEngine.SpriteParticleRegistration<SimpleParticleType>() {
            @Override
            ParticleProvider<SimpleParticleType> create(SpriteSet sprites) {
                return new DrippingAshParticle.DrippingAshParticleProvider(sprites)
            }
        })
    }
}
