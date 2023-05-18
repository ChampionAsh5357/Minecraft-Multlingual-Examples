/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.registrar

import groovy.transform.CompileStatic
import net.minecraft.world.food.FoodProperties

/**
 * A utility class used to hold any non-registry objects for this mod.
 */
@CompileStatic
final class GeneralRegistrar {

    /**
     * A dummy method used to load the static objects in this class.
     */
    static void registerGeneral() {}

    @Lazy static final FoodProperties WAFFLE = { new FoodProperties.Builder().nutrition(5).saturationMod(0.8f).build() }()
    @Lazy static final FoodProperties WAFFLE_CONE = { new FoodProperties.Builder().nutrition(3).saturationMod(0.9f).build() }()
    @Lazy static final FoodProperties SNOW_CONE = { new FoodProperties.Builder().nutrition(4).saturationMod(1f).build() }()
    @Lazy static final FoodProperties ICE_CREAM_SANDWICH = { new FoodProperties.Builder().nutrition(6).saturationMod(0.6f).build() }()
}
