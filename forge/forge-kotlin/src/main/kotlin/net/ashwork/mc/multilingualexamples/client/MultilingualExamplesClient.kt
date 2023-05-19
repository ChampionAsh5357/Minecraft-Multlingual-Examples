/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.client

import net.ashwork.mc.multilingualexamples.client.model.ArmorModelManager
import net.ashwork.mc.multilingualexamples.client.particle.DrippingAshParticleProvider
import net.ashwork.mc.multilingualexamples.registrar.DRIPPING_ASH
import net.minecraftforge.client.event.RegisterParticleProvidersEvent
import net.minecraftforge.eventbus.api.IEventBus

/**
 * An isolated class for initialization of anything the mod needs specifically
 * for the client. This should only be referenced through a sided check.
 */
object MultilingualExamplesClient{

    private lateinit var modelManager: ArmorModelManager

    /**
     * Initializes the client handler.
     *
     * @param modBus the mod's event bus
     * @param forgeBus the forge event bus
     */
    fun init(modBus: IEventBus, forgeBus: IEventBus) {
        this.modelManager = ArmorModelManager(modBus)

        modBus.addListener(::onRegisterParticleFactories)
    }

    /**
     * Returns the armor model manager.
     *
     * @return the armor model manager
     */
    fun armorModelManager(): ArmorModelManager = modelManager

    /**
     * An event used to register particle factories.
     *
     * @param event an event instance
     */
    private fun onRegisterParticleFactories(event: RegisterParticleProvidersEvent) {
        /*
        * Register our particle factory.
        *
        * Since this is using net.minecraft.client.particle.ParticleEngine.SpriteParticleRegistration, this will need
        * a JSON to reference the particle sprites. This will use the registry name of the particle type itself and be
        * located within the 'particles' directory within assets.
        *
        * Textures referenced in the JSON will be in the 'particle' directory within textures.
        */
        event.registerSpriteSet(DRIPPING_ASH.get(), ::DrippingAshParticleProvider)
    }
}
