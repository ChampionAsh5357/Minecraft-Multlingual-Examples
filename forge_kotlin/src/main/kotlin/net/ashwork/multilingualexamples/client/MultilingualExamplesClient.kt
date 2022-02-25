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

import net.ashwork.multilingualexamples.client.particle.DrippingAshParticleProvider
import net.ashwork.multilingualexamples.registrar.DRIPPING_ASH
import net.minecraft.client.Minecraft
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent
import net.minecraftforge.eventbus.api.IEventBus

/**
 * An isolated class for initialization of anything the mod needs specifically
 * for the client. This should only be referenced through a sided check.
 *
 * @param modBus the mod's event bus
 * @param forgeBus the forge event bus
 */
class MultilingualExamplesClient(private val modBus: IEventBus, private val forgeBus: IEventBus) {

    init {
        this.modBus.addListener(this::onRegisterParticleFactories)
    }

    /**
     * An event used to register particle factories.
     *
     * @param event an event instance
     */
    private fun onRegisterParticleFactories(event: ParticleFactoryRegisterEvent) {
        /*
        * Register our particle factory.
        *
        * Since this is using net.minecraft.client.particle.ParticleEngine.SpriteParticleRegistration, this will need
        * a JSON to reference the particle sprites. This will use the registry name of the particle type itself and be
        * located within the 'particles' directory within assets.
        *
        * Textures referenced in the JSON will be in the 'particle' directory within textures.
        */
        Minecraft.getInstance().particleEngine.register(DRIPPING_ASH.get(), ::DrippingAshParticleProvider)
    }
}