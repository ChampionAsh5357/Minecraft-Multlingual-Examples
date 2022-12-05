/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data.loot;

import net.ashwork.mc.multilingualexamples.registrar.BlockRegistrar;
import net.ashwork.mc.multilingualexamples.registrar.Registrars;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

/**
 * A loot table provider for {@link LootContextParamSets#BLOCK}.
 */
public class ExampleBlockLoot extends BlockLoot {

    @Override
    protected void addTables() {
        this.dropSelf(BlockRegistrar.WAFFLE.get());
        this.add(BlockRegistrar.SQUISHED_WAFFLE.get(), BlockLoot::createSlabItemTable);
    }

    @NotNull
    @Override
    protected Iterable<Block> getKnownBlocks() {
        /*
        This method is overridden to make sure we are only checking the block
        added to this mod that an associated loot table was generated.
         */
        return Registrars.BLOCKS.getEntries().stream().map(RegistryObject::get).toList();
    }
}
