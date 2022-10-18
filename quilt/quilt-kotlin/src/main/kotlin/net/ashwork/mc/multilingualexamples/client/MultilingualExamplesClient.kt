/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.client

import net.ashwork.mc.multilingualexamples.client.model.ArmorModelManager
import net.ashwork.mc.multilingualexamples.client.particle.DrippingAshParticleProvider
import net.ashwork.mc.multilingualexamples.registrar.DRIPPING_ASH
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry
import org.quiltmc.loader.api.ModContainer
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer

/**
 * An isolated class for initialization of anything the mod needs specifically
 * for the client. The fully qualified name of this class must match that within
 * 'entrypoints.client_init'. Any client entry point must implement [ClientModInitializer].
 */
internal class MultilingualExamplesClient: ClientModInitializer {

    override fun onInitializeClient(mod: ModContainer) {
        armorModelManager().init()

        /*
         * Register our particle factory.
         *
         * Since this is using net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry.PendingParticleFactory, this will need
         * a JSON to reference the particle sprites. This will use the registry name of the particle type itself and be
         * located within the 'particles' directory within assets.
         *
         * Textures referenced in the JSON will be in the 'particle' directory within textures.
         */
        ParticleFactoryRegistry.getInstance().register(DRIPPING_ASH, ::DrippingAshParticleProvider)
    }

    companion object {
        private val modelManager: ArmorModelManager = ArmorModelManager()

        /**
         * Returns the armor model manager.
         *
         * @return the armor model manager
         */
        fun armorModelManager(): ArmorModelManager = modelManager
    }
}
