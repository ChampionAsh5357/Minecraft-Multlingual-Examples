/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.client

import net.ashwork.mc.multilingualexamples.client.particle.DrippingAshParticleProvider
import net.ashwork.mc.multilingualexamples.registrar.DRIPPING_ASH
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