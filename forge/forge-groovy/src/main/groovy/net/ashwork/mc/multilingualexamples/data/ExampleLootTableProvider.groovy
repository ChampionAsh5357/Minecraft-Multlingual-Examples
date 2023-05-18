/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data

import com.mojang.datafixers.util.Pair
import groovy.transform.CompileStatic
import net.ashwork.mc.multilingualexamples.data.loot.ExampleBlockLoot
import net.minecraft.data.DataGenerator
import net.minecraft.data.loot.LootTableProvider
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.LootTables
import net.minecraft.world.level.storage.loot.ValidationContext
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets

import java.util.function.BiConsumer
import java.util.function.Consumer
import java.util.function.Supplier

/**
 * A data provider which generates loot tables for this mod.
 */
class ExampleLootTableProvider extends LootTableProvider {

    /**
     * Default constructor.
     *
     * @param gen the generator being written to
     */
    ExampleLootTableProvider(DataGenerator gen) {
        super(gen)
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        /*
        We need to provide a list of loot tables to be built for a specific
        context. The loot tables being built will then be supplied to the writer
        to generate the associated files.
         */
        return [
                Pair.of(ExampleBlockLoot::new as Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSets.BLOCK)
        ]
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> tables, ValidationContext ctx) {
        /*
        We override this method since the validation also checks built-in, special
        case loot tables. The override removes the check.
         */
        tables.forEach { name, table -> LootTables.validate(ctx, name, table) }
    }
}
