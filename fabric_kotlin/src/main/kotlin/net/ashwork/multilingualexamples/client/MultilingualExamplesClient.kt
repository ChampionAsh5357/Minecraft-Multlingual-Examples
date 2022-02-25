package net.ashwork.multilingualexamples.client

import net.ashwork.multilingualexamples.client.particle.DrippingAshParticleProvider
import net.ashwork.multilingualexamples.registrar.DRIPPING_ASH
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry

/**
 * An isolated class for initialization of anything the mod needs specifically
 * for the client. The fully qualified name of this class must match that within
 * 'entrypoints.client'. Any client entry point must implement [ClientModInitializer].
 */
internal object MultilingualExamplesClient: ClientModInitializer {

    override fun onInitializeClient() {
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
}