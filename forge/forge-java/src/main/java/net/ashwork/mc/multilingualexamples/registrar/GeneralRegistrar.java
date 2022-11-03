package net.ashwork.mc.multilingualexamples.registrar;

import net.minecraft.world.food.FoodProperties;

/**
 * A utility class used to hold any non-registry objects for this mod.
 */
public class GeneralRegistrar {

    /**
     * A dummy method used to load the static objects in this class.
     */
    public static void register() {}

    public static final FoodProperties WAFFLE = new FoodProperties.Builder().nutrition(5).saturationMod(0.8f).build();
}
