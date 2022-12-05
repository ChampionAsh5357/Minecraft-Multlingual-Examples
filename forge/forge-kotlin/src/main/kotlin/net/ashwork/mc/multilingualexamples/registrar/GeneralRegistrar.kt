/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.registrar

import net.minecraft.world.food.FoodProperties

/*
 * A utility file used to hold any non-registry objects for this mod.
 */

/**
 * A dummy method used to load the static objects in this class.
 */
internal fun registerGeneral() = run { }

/*
We use lazy values here to mitigate issues of weird registry order as depending
on how some abstractions are written, the non-registry objects can be loaded at
the wrong point in time, causing exceptions to be thrown.
 */

val WAFFLE_FOOD: FoodProperties by lazy {
    FoodProperties.Builder().nutrition(5).saturationMod(0.8f).build()
}
val WAFFLE_CONE_FOOD: FoodProperties by lazy {
    FoodProperties.Builder().nutrition(3).saturationMod(0.9f).build()
}
val SNOW_CONE_FOOD: FoodProperties by lazy {
    FoodProperties.Builder().nutrition(4).saturationMod(1f).build()
}
val ICE_CREAM_SANDWICH_FOOD: FoodProperties by lazy {
    FoodProperties.Builder().nutrition(6).saturationMod(0.6f).build()
}
