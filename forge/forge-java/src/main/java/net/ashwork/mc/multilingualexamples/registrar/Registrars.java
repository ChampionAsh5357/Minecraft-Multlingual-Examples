/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.registrar;

import net.ashwork.mc.multilingualexamples.MultilingualExamples;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * A class used to create registrars to register objects for this mod. These
 * must be attached to the mod event bus through some call within the main mod
 * constructor.
 */
public class Registrars {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MultilingualExamples.ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MultilingualExamples.ID);
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MultilingualExamples.ID);

    /**
     * An initialization method used to add registrars to the event bus and
     * load the objects to be registered.
     *
     * @param modBus the mod event bus
     */
    public static void init(IEventBus modBus) {
        /*
        We load the registrars first from the items or blocks such that we never
        run into race conditions or deadlocks due to circular loading. Some mod
        loaders handle this already, so it's very difficult to cause these issues,
        but others need to be conscientious.
         */

        // Add registrars to mod bus
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
        PARTICLE_TYPES.register(modBus);

        // Load registrar object classes
        GeneralRegistrar.register();
        BlockRegistrar.register();
        ItemRegistrar.register();
        ParticleTypeRegistrar.register();

        // Add events
        modBus.addListener(Registrars::buildTabContents);
    }

    /**
     * An event listener that, when fired, adds content to a {@link net.minecraft.world.item.CreativeModeTab}.
     *
     * @param event the {@link net.minecraftforge.event.CreativeModeTabEvent.BuildContents} event
     */
    private static void buildTabContents(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ItemRegistrar.ASH);
        } else if (event.getTab() == CreativeModeTabs.COMBAT) {
            event.accept(ItemRegistrar.COLLAGE_HELMET);
            event.accept(ItemRegistrar.COLLAGE_CHESTPLATE);
            event.accept(ItemRegistrar.COLLAGE_LEGGINGS);
            event.accept(ItemRegistrar.COLLAGE_BOOTS);
        } else if (event.getTab() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(BlockRegistrar.SQUISHED_WAFFLE);
            event.accept(BlockRegistrar.WAFFLE);
            event.accept(ItemRegistrar.WAFFLE_MIX);
            event.accept(ItemRegistrar.WAFFLE_CONE);
            event.accept(ItemRegistrar.SNOW_CONE);
            event.accept(ItemRegistrar.ICE_CREAM_SANDWICH);
        }
    }
}
