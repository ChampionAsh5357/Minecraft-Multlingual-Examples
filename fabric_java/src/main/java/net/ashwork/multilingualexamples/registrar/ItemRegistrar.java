/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357.
 *
 * To the extent possible under law, the author(s) have dedicated all copyright and related
 * and neighboring rights to this software to the public domain worldwide. This software is
 * distributed without any warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication along with this
 * software. If not, see https://creativecommons.org/publicdomain/zero/1.0/.
 */

package net.ashwork.multilingualexamples.registrar;

import net.ashwork.multilingualexamples.MultilingualExamples;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

/**
 * A utility class used to hold and register all items for this mod.
 */
public final class ItemRegistrar {

    public static final Item ASH = register("ash", new Item(new Item.Properties().tab(CreativeModeTab.TAB_BREWING)));

    /**
     * Registers an object to the item registry.
     *
     * @param name the registry name of the object
     * @param obj the object instance
     * @param <T> the type of the object
     * @return the object instance being registered
     */
    private static <T extends Item> T register(final String name, final T obj) {
        return Registry.register(Registry.ITEM, new ResourceLocation(MultilingualExamples.ID, name), obj);
    }
}
