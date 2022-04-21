/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.util

import net.minecraft.resources.ResourceLocation
import net.minecraftforge.registries.IForgeRegistryEntry
import net.minecraftforge.registries.RegistryObject

/**
 * Prefixes the path of the resource location if there is none already.
 *
 * @param prefix the name of the prefix
 * @return a new [ResourceLocation] with the path prefixed
 */
infix fun ResourceLocation.prefix(prefix: String): ResourceLocation =
    if (this.path.contains("/")) this else ResourceLocation(this.namespace, "${prefix}/${this.path}")

/**
 * Wraps a registry object to a Kotlin supplier.
 */
val <T : IForgeRegistryEntry<T>> RegistryObject<T>.kt: () -> T
    get() = { this.get() }