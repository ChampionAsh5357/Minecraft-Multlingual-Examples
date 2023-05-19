/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data.loot;

import net.ashwork.mc.multilingualexamples.registrar.BlockRegistrar;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.function.BiConsumer;

/**
 * A loot table provider for {@link LootContextParamSets#BLOCK}.
 */
public class ExampleBlockLootTableProvider extends FabricBlockLootTableProvider {

    /**
     * A simple constructor.
     *
     * @param output the output of the data generator
     */
    public ExampleBlockLootTableProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate() {
        this.dropSelf(BlockRegistrar.WAFFLE);
        this.add(BlockRegistrar.SQUISHED_WAFFLE, this::createSlabItemTable);
    }

    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> writer) {
        /*
        Fabric has a weird bug that requires this implementation. Essentially,
        #accept is #generate in Yarn mappings. The provider implemented a
        consumer, meaning that #accept in Yarn mappings was properly overridden.
        However, in Mojmaps, this is not the case and the method needs to be
        manually delegated.

        This would probably never come about during testing in a normal situation.
        This method would need to be removed when in Yarn mappings as otherwise it
        would be a recursive call.
         */
        this.generate(writer);
    }
}
