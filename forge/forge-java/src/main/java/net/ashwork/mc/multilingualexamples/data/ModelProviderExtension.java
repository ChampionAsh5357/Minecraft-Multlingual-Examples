/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data;

import net.minecraft.resources.ResourceLocation;

/**
 * A simple extension interface to add methods that are currently made private
 * by Forge's providers.
 */
public interface ModelProviderExtension {

    /**
     * Prefixes the path of the resource location if there is none already.
     *
     * @param rl the location
     * @param prefix the name of the prefix
     * @return a new {@link ResourceLocation} with the path prefixed
     */
    default ResourceLocation prefix(final ResourceLocation rl, final String prefix) {
        return rl.getPath().contains("/") ? rl : new ResourceLocation(rl.getNamespace(), prefix + "/" + rl.getPath());
    }
}
