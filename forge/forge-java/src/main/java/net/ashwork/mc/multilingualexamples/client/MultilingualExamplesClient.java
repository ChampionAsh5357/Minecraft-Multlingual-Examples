/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.client;

import net.ashwork.mc.multilingualexamples.client.model.ArmorModelManager;
import net.ashwork.mc.multilingualexamples.client.particle.DrippingAshParticle;
import net.ashwork.mc.multilingualexamples.registrar.ParticleTypeRegistrar;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.IEventBus;

/**
 * An isolated class for initialization of anything the mod needs specifically
 * for the client. This should only be referenced through a sided check.
 */
public class MultilingualExamplesClient {

    private static MultilingualExamplesClient _instance;
    private final ArmorModelManager modelManager;

    /**
     * Default constructor.
     *
     * @param modBus the mod's event bus
     * @param forgeBus the forge event bus
     */
    public MultilingualExamplesClient(final IEventBus modBus, final IEventBus forgeBus) {
        _instance = this;
        this.modelManager = new ArmorModelManager(modBus);

        modBus.addListener(this::onRegisterParticleFactories);
    }

    /**
     * {@return the client instance of this mod}
     */
    public static MultilingualExamplesClient instance() {
        return _instance;
    }

    /**
     * {@return the armor model manager}
     */
    public ArmorModelManager armorModelManager() {
        return this.modelManager;
    }

    /**
     * An event used to register particle factories.
     *
     * @param event an event instance
     */
    private void onRegisterParticleFactories(final RegisterParticleProvidersEvent event) {
        /*
         * Register our particle factory.
         *
         * Since this is using net.minecraft.client.particle.ParticleEngine.SpriteParticleRegistration, this will need
         * a JSON to reference the particle sprites. This will use the registry name of the particle type itself and be
         * located within the 'particles' directory within assets.
         *
         * Textures referenced in the JSON will be in the 'particle' directory within textures.
         */
        event.register(ParticleTypeRegistrar.DRIPPING_ASH.get(), DrippingAshParticle.DrippingAshParticleProvider::new);
    }
}
