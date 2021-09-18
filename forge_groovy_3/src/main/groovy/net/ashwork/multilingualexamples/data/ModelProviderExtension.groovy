/*
 * Multilingual Examples
 * Written 2021-2021 by ChampionAsh5357.
 *
 * To the extent possible under law, the author(s) have dedicated all copyright and related
 * and neighboring rights to this software to the public domain worldwide. This software is
 * distributed without any warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication along with this
 * software. If not, see https://creativecommons.org/publicdomain/zero/1.0/.
 */

package net.ashwork.multilingualexamples.data

import net.minecraft.resources.ResourceLocation

/**
 * A simple extension interface to add methods that are currently made private
 * by Forge's providers.
 */
interface ModelProviderExtension {

    /**
     * Prefixes the path of the resource location if there is none already.
     *
     * @param rl the location
     * @param prefix the name of the prefix
     * @return a new {@link ResourceLocation} with the path prefixed
     */
    default ResourceLocation prefix(final ResourceLocation rl, final String prefix) {
        return rl.getPath().contains("/") ? rl : new ResourceLocation(rl.getNamespace(), "${prefix}/${rl.getPath()}")
    }
}