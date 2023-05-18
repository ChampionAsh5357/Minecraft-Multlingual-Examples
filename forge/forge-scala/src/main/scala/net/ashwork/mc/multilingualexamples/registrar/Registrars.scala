/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.registrar

import net.ashwork.mc.multilingualexamples.MultilingualExamples
import net.minecraft.core.particles.ParticleType
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.{DeferredRegister, ForgeRegistries}

/*
 * An object used to create registrars to register objects for this mod. These
 * must be attached to the mod event bus through some call within the main mod
 * constructor.
 */
object Registrars {

    val BLOCK_REGISTRAR: DeferredRegister[Block] = DeferredRegister.create(ForgeRegistries.BLOCKS, MultilingualExamples.ID)
    val ITEM_REGISTRAR: DeferredRegister[Item] = DeferredRegister.create(ForgeRegistries.ITEMS, MultilingualExamples.ID)
    val PARTICLE_TYPE_REGISTRAR: DeferredRegister[ParticleType[_]] = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MultilingualExamples.ID)

    /**
     * An initialization method used to add registrars to the event bus and
     * load the objects to be registered.
     *
     * @param modBus the mod event bus
     */
    def init(modBus: IEventBus): Unit = {
        /*
        We load the registrars first from the items or blocks such that we never
        run into race conditions or deadlocks due to circular loading. Some mod
        loaders handle this already, so it's very difficult to cause these issues,
        but others need to be conscientious.
         */

        // Add registrars to mod bus
        BLOCK_REGISTRAR.register(modBus)
        ITEM_REGISTRAR.register(modBus)
        PARTICLE_TYPE_REGISTRAR.register(modBus)

        // Load registrar object classes
        GeneralRegistrar.register()
        BlockRegistrar.register()
        ItemRegistrar.register()
        ParticleTypeRegistrar.register()
    }
}
