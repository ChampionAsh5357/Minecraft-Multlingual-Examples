/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.client

import net.ashwork.mc.multilingualexamples.client.model.ArmorModelManager
import net.ashwork.mc.multilingualexamples.client.particle.DrippingAshParticle
import net.ashwork.mc.multilingualexamples.registrar.ParticleTypeRegistrar
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry
import net.minecraft.core.particles.SimpleParticleType
import org.quiltmc.loader.api.ModContainer
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer
/**
 * An isolated class for initialization of anything the mod needs specifically
 * for the client. The fully qualified name of this class must match that within
 * {@code entrypoints.client_init}. Any client entry point must implement {@link ClientModInitializer}.
 */
class MultilingualExamplesClient implements ClientModInitializer {

    private static MultilingualExamplesClient _instance
    private final ArmorModelManager modelManager

    /**
     * Default constructor.
     */
    MultilingualExamplesClient() {
        _instance = this
        this.modelManager = new ArmorModelManager()
    }

    /**
     * Returns the client instance of this mod.
     *
     * @return the client instance of this mod
     */
    static MultilingualExamplesClient instance() {
        return _instance
    }

    /**
     * Returns the armor model manager.
     *
     * @return the armor model manager
     */
    ArmorModelManager armorModelManager() {
        return this.modelManager
    }

    @Override
    void onInitializeClient(final ModContainer mod) {
        this.armorModelManager().init()

        /*
         * Register our particle factory.
         *
         * Since this is using net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry.PendingParticleFactory, this will need
         * a JSON to reference the particle sprites. This will use the registry name of the particle type itself and be
         * located within the 'particles' directory within assets.
         *
         * Textures referenced in the JSON will be in the 'particle' directory within textures.
         */
        ParticleFactoryRegistry.getInstance().register(ParticleTypeRegistrar.DRIPPING_ASH,
                { new DrippingAshParticle.DrippingAshParticleProvider(it) } as ParticleFactoryRegistry.PendingParticleFactory<SimpleParticleType>)
    }
}
