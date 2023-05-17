/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.client.particle;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * The base particle class. All rendering related to the particle occurs here.
 */
@ParametersAreNonnullByDefault
public class DrippingAshParticle extends TextureSheetParticle {

    private final SpriteSet sprites;

    /**
     * Default constructor.
     *
     * @param level the client level the particle is rendering in
     * @param xOriginal the spawned x position
     * @param yOriginal the spawned y position
     * @param zOriginal the spawned z position
     * @param xVelocity the initial x velocity
     * @param yVelocity the initial y velocity
     * @param zVelocity the initial z velocity
     * @param sprites the referenced sprites that will be used for rendering
     */
    public DrippingAshParticle(final ClientLevel level, final double xOriginal, final double yOriginal, final double zOriginal,
                               final double xVelocity, final double yVelocity, final double zVelocity, final SpriteSet sprites) {
        super(level, xOriginal, yOriginal, zOriginal, xVelocity, yVelocity, zVelocity);

        // Set some basic params
        this.setSize(0.01f, 0.01f);
        this.gravity = 0.4f;
        this.sprites = sprites;

        // Set this here otherwise NPE will be thrown since rendering happens before initial tick
        this.setSpriteFromAge(this.sprites);
    }

    @Override
    public void tick() {
        super.tick();

        // Call every tick to change the particle over time
        this.setSpriteFromAge(this.sprites);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    /**
     * The provider used to register the factory to create the rendered {@link DrippingAshParticle} instance.
     */
    public static class DrippingAshParticleProvider implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet sprites;

        /**
         * Constructor to be passed as a method reference to
         * {@link net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry#register(ParticleType, ParticleFactoryRegistry.PendingParticleFactory)}.
         *
         * @param sprites the referenced sprites that will be used for rendering
         */
        public DrippingAshParticleProvider(final SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Nullable
        @Override
        public Particle createParticle(final SimpleParticleType type, final ClientLevel level,
                                       final double xOriginal, final double yOriginal, final double zOriginal,
                                       final double xVelocity, final double yVelocity, final double zVelocity) {
            return new DrippingAshParticle(level, xOriginal, yOriginal, zOriginal, xVelocity, yVelocity, zVelocity, this.sprites);
        }
    }
}
