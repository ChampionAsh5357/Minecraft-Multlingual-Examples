package net.ashwork.mc.multilingualexamples.registrar

import net.minecraft.world.food.FoodProperties

/**
 * A utility class used to hold any non-registry objects for this mod.
 */
object GeneralRegistrar {

    /**
     * A dummy method used to load the static objects in this class.
     */
    def register(): Unit = {}

    /*
    We use lazy values here to mitigate issues of weird registry order as depending
    on how some abstractions are written, the non-registry objects can be loaded at
    the wrong point in time, causing exceptions to be thrown.
    */

    final lazy val WAFFLE: FoodProperties = FoodProperties.Builder().nutrition(5).saturationMod(0.8f).build()
    final lazy val WAFFLE_CONE: FoodProperties = FoodProperties.Builder().nutrition(3).saturationMod(0.9f).build()
    final lazy val SNOW_CONE: FoodProperties = FoodProperties.Builder().nutrition(4).saturationMod(1f).build()
    final lazy val ICE_CREAM_SANDWICH: FoodProperties = FoodProperties.Builder().nutrition(6).saturationMod(0.6f).build()
}
