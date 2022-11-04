package net.ashwork.mc.multilingualexamples.registrar;

import net.minecraft.world.food.FoodProperties;
import net.minecraftforge.common.util.Lazy;

/**
 * A utility class used to hold any non-registry objects for this mod.
 */
public class GeneralRegistrar {

    /**
     * A dummy method used to load the static objects in this class.
     */
    public static void register() {}

    /*
    We use lazy values here to mitigate issues of weird registry order as depending
    on how some abstractions are written, the non-registry objects can be loaded at
    the wrong point in time, causing exceptions to be thrown.
     */

    public static final Lazy<FoodProperties> WAFFLE =
            Lazy.of(() -> new FoodProperties.Builder().nutrition(5).saturationMod(0.8f).build());
}
