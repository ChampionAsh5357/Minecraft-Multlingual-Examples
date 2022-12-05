/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data

import com.mojang.datafixers.util.Pair
import net.ashwork.mc.multilingualexamples.data.loot.ExampleBlockLoot
import net.minecraft.data.DataGenerator
import net.minecraft.data.loot.LootTableProvider
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.storage.loot.{LootTable, LootTables, ValidationContext}
import net.minecraft.world.level.storage.loot.parameters.{LootContextParamSet, LootContextParamSets}

import java.util
import java.util.function.{BiConsumer, Consumer, Supplier}
import scala.jdk.CollectionConverters._
import scala.jdk.FunctionConverters._

/**
 * A data provider which generates loot tables for this mod.
 *
 * @param gen the generator being written to
 */
class ExampleLootTableProvider(gen: DataGenerator) extends LootTableProvider(gen) {

    /*
    We need to provide a list of loot tables to be built for a specific
    context. The loot tables being built will then be supplied to the writer
    to generate the associated files.
     */
    override def getTables: util.List[Pair[Supplier[Consumer[BiConsumer[ResourceLocation, LootTable.Builder]]], LootContextParamSet]] =
        Seq(
            Pair.of((() => ExampleBlockLoot()).asJavaSupplier, LootContextParamSets.BLOCK)
        ).asJava

    /*
    We override this method since the validation also checks built-in, special
    case loot tables. The override removes the check.
     */
    override def validate(tables: util.Map[ResourceLocation, LootTable], ctx: ValidationContext): Unit =
        tables.forEach((name, table) => LootTables.validate(ctx, name, table))
}
