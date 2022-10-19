/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.util

import net.minecraft.resources.ResourceLocation

/**
 * A simple extension holder to add convenient methods to existing classes.
 */
object MultilingualExtensions {

    /**
     * Extensions for [[ResourceLocation]]s.
     */
    extension (rl: ResourceLocation) {
        
        /**
         * Prefixes the path of the resource location if there is none already.
         *
         * @param prefix the name of the prefix
         * @return a new [[ResourceLocation]] with the path prefixed
         */
        infix def prefix(prefix: String): ResourceLocation =
            if(rl.getPath.contains("/")) rl else ResourceLocation(rl.getNamespace, s"$prefix/${rl.getPath}")
    }
}
