/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
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
 *
 * @param modBus the mod's event bus
 * @param forgeBus the forge event bus
 */
class MultilingualExamplesClient(modBus: IEventBus, forgeBus: IEventBus) {

    private val modelManager: ArmorModelManager = ArmorModelManager(modBus)

    init {
        instance = this

        modBus.addListener(this::onRegisterParticleFactories)
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
        event.register(DRIPPING_ASH.get(), ::DrippingAshParticleProvider)
    }
}

private lateinit var instance: MultilingualExamplesClient

/**
 * Returns the client instance of this mod.
 *
 * @return the client instance of this mod
 */
fun clientInstance(): MultilingualExamplesClient = instance
