/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data

import net.minecraft.resources.ResourceLocation

/**
 * A simple extension interface to add methods that are currently made private
 * by Forge's providers.
 */
trait ModelProviderExtension {

    /**
     * Prefixes the path of the resource location if there is none already.
     *
     * @param rl     the location
     * @param prefix the name of the prefix
     * @return a new [[ResourceLocation]] with the path prefixed
     */
    def prefix(rl: ResourceLocation, prefix: String): ResourceLocation =
        if(rl.getPath.contains("/")) rl else new ResourceLocation(rl.getNamespace, s"${prefix}/${rl.getPath}")
}
