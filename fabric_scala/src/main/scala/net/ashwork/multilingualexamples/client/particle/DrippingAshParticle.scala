package net.ashwork.multilingualexamples.client.particle

import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.particle._
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
                          xVelocity: Double, yVelocity: Double, zVelocity: Double, private val sprites: SpriteSet)
        extends TextureSheetParticle(level, xOriginal, yOriginal, zOriginal, xVelocity, yVelocity, zVelocity) {

    // Set some basic params
    this.setSize(0.01f, 0.01f)
    this.gravity = 0.4f

    // Set this here otherwise NPE will be thrown since rendering happens before initial tick
    this.setSpriteFromAge(this.sprites)

    override def tick(): Unit = {
        super.tick()

        // Call every tick to change the particle over time
        this.setSpriteFromAge(this.sprites)
    }

    override def getRenderType: ParticleRenderType = ParticleRenderType.PARTICLE_SHEET_OPAQUE
}

object DrippingAshParticle {

    /**
     * The provider used to register the factory to create the rendered [[DrippingAshParticle]] instance.
     * This is to be passed as a method reference to [[net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry]].
     *
     * @param sprites the referenced sprites that will be used for rendering
     */
    class DrippingAshParticleProvider(private val sprites: SpriteSet) extends ParticleProvider[SimpleParticleType] {

        override def createParticle(particleType: SimpleParticleType, level: ClientLevel,
                                    xOriginal: Double, yOriginal: Double, zOriginal: Double,
                                    xVelocity: Double, yVelocity: Double, zVelocity: Double
                                   ): Particle = new DrippingAshParticle(level, xOriginal, yOriginal, zOriginal, xVelocity, yVelocity, zVelocity, this.sprites)
    }
}
