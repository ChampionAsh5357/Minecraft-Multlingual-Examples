package net.ashwork.mc.multilingualexamples.registrar;

import com.google.common.base.Suppliers;
import net.minecraft.world.food.FoodProperties;

import java.util.function.Supplier;

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

    public static final Supplier<FoodProperties> WAFFLE =
            Suppliers.memoize(() -> new FoodProperties.Builder().nutrition(5).saturationMod(0.8f).build());
    public static final Supplier<FoodProperties> WAFFLE_CONE =
            Suppliers.memoize(() -> new FoodProperties.Builder().nutrition(3).saturationMod(0.9f).build());
    public static final Supplier<FoodProperties> SNOW_CONE =
            Suppliers.memoize(() -> new FoodProperties.Builder().nutrition(4).saturationMod(1f).build());
    public static final Supplier<FoodProperties> ICE_CREAM_SANDWICH =
            Suppliers.memoize(() -> new FoodProperties.Builder().nutrition(6).saturationMod(0.6f).build());
}
