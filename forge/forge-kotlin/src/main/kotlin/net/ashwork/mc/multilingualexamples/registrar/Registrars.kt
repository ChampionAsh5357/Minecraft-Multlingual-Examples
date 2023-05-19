/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.registrar

import net.ashwork.mc.multilingualexamples.ID
import net.minecraft.world.item.CreativeModeTabs
import net.minecraftforge.event.CreativeModeTabEvent.BuildContents
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

    // Add events
    modBus.addListener(::buildTabContents)
}

/**
 * An event listener that, when fired, adds content to a [net.minecraft.world.item.CreativeModeTab].
 *
 * @param event the [BuildContents] event
 */
private fun buildTabContents(event: BuildContents) {
    when (event.tab) {
        CreativeModeTabs.INGREDIENTS -> event.accept(ASH)
        CreativeModeTabs.COMBAT -> {
            event.accept(COLLAGE_HELMET)
            event.accept(COLLAGE_CHESTPLATE)
            event.accept(COLLAGE_LEGGINGS)
            event.accept(COLLAGE_BOOTS)
        }
        CreativeModeTabs.FOOD_AND_DRINKS -> {
            event.accept(SQUISHED_WAFFLE)
            event.accept(WAFFLE)
            event.accept(WAFFLE_MIX)
            event.accept(WAFFLE_CONE)
            event.accept(SNOW_CONE)
            event.accept(ICE_CREAM_SANDWICH)
        }
    }
}
