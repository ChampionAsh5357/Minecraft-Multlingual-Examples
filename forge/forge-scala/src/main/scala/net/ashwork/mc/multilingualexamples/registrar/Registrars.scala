/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.registrar

import net.ashwork.mc.multilingualexamples.MultilingualExamples
import net.minecraft.core.particles.ParticleType
import net.minecraft.world.item.{CreativeModeTabs, Item}
import net.minecraft.world.level.block.Block
import net.minecraftforge.event.CreativeModeTabEvent.BuildContents
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

        // Add events
        modBus.addListener(this.buildTabContents)
    }

    /**
     * An event listener that, when fired, adds content to a [[net.minecraft.world.item.CreativeModeTab]].
     *
     * @param event the [[BuildContents]] event
     */
    private def buildTabContents(event: BuildContents): Unit = {
        event.getTab match
            case CreativeModeTabs.INGREDIENTS => event.accept(ItemRegistrar.ASH)
            case CreativeModeTabs.COMBAT =>
                event.accept(ItemRegistrar.COLLAGE_HELMET)
                event.accept(ItemRegistrar.COLLAGE_CHESTPLATE)
                event.accept(ItemRegistrar.COLLAGE_LEGGINGS)
                event.accept(ItemRegistrar.COLLAGE_BOOTS)
            case CreativeModeTabs.FOOD_AND_DRINKS =>
                event.accept(BlockRegistrar.SQUISHED_WAFFLE)
                event.accept(BlockRegistrar.WAFFLE)
                event.accept(ItemRegistrar.WAFFLE_MIX)
                event.accept(ItemRegistrar.WAFFLE_CONE)
                event.accept(ItemRegistrar.SNOW_CONE)
                event.accept(ItemRegistrar.ICE_CREAM_SANDWICH)
            case _ =>
    }
}
