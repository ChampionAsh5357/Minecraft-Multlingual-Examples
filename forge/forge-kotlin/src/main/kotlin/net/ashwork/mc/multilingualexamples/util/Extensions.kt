/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.util

import net.minecraft.resources.ResourceLocation

/**
 * Prefixes the path of the resource location if there is none already.
 *
 * @param prefix the name of the prefix
 * @return a new [ResourceLocation] with the path prefixed
 */
infix fun ResourceLocation.prefix(prefix: String): ResourceLocation =
    if (this.path.contains("/")) this else ResourceLocation(this.namespace, "${prefix}/${this.path}")
