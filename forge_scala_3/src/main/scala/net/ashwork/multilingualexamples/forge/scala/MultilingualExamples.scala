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

package net.ashwork.multilingualexamples.forge.scala

import net.minecraftforge.fml.common.Mod

/**
 * The main mod class. This is where the initialization of the mod happens.
 * The mod id supplied in the annotation must match that within the [[mods.toml]].
 */
@Mod(MultilingualExamples.ID)
final class MultilingualExamples

/**
 * The global instance of our main mod class. Used to store any constants.
 */
final object MultilingualExamples {
    /**
     * The modid of our mod.
     */
    final val ID = "multilingual_examples_forge_scala"
}
