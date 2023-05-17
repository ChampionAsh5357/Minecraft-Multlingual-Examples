/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.registrar

import net.ashwork.mc.multilingualexamples.ID
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

/*
 * A file used to create registrars to register objects for this mod. These
 * must be attached to the mod event bus through some call within the main mod
 * constructor.
 */

internal val BLOCK_REGISTRAR = DeferredRegister.create(ForgeRegistries.BLOCKS, ID)
internal val ITEM_REGISTRAR = DeferredRegister.create(ForgeRegistries.ITEMS, ID)
internal val PARTICLE_TYPE_REGISTRAR = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ID)

/**
 * An initialization method used to add registrars to the event bus and
 * load the objects to be registered.
 *
 * @param modBus the mod event bus
 */
internal fun initRegistrars(modBus: IEventBus) {
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
    registerGeneral()
    registerBlocks()
    registerItems()
    registerParticleTypes()
}
