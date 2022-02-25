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

package net.ashwork.multilingualexamples.client.particle

import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.particle.*
import net.minecraft.core.particles.SimpleParticleType

/**
 * The base particle class. All rendering related to the particle occurs here.
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
class DrippingAshParticle(level: ClientLevel, xOriginal: Double, yOriginal: Double, zOriginal: Double,
                          xVelocity: Double, yVelocity: Double, zVelocity: Double, private val sprites: SpriteSet):
    TextureSheetParticle(level, xOriginal, yOriginal, zOriginal, xVelocity, yVelocity, zVelocity) {

    init {
        // Set some basic params
        this.setSize(0.01f, 0.01f)
        this.gravity = 0.4f

        // Set this here otherwise NPE will be thrown since rendering happens before initial tick
        this.setSpriteFromAge(this.sprites)
    }

    override fun tick() {
        super.tick()

        // Call every tick to change the particle over time
        this.setSpriteFromAge(this.sprites)
    }

    override fun getRenderType(): ParticleRenderType = ParticleRenderType.PARTICLE_SHEET_OPAQUE
}

/**
 * The provider used to register the factory to create the rendered [DrippingAshParticle] instance.
 * This is to be passed as a method reference to [ParticleEngine.register].
 *
 * @param sprites the referenced sprites that will be used for rendering
 */
class DrippingAshParticleProvider(private val sprites: SpriteSet): ParticleProvider<SimpleParticleType> {

    override fun createParticle(type: SimpleParticleType, level: ClientLevel,
        xOriginal: Double, yOriginal: Double, zOriginal: Double,
        xVelocity: Double, yVelocity: Double, zVelocity: Double
    ): Particle = DrippingAshParticle(level, xOriginal, yOriginal, zOriginal, xVelocity, yVelocity, zVelocity, this.sprites)

}
