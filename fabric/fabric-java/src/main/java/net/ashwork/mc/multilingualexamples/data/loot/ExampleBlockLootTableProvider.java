package net.ashwork.mc.multilingualexamples.data.loot;

import net.ashwork.mc.multilingualexamples.registrar.BlockRegistrar;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

/**
 * A loot table provider for {@link LootContextParamSets#BLOCK}.
 */
public class ExampleBlockLootTableProvider extends FabricBlockLootTableProvider {

    /**
     * A simple constructor.
     *
     * @param gen the generator being written to
     */
    public ExampleBlockLootTableProvider(FabricDataGenerator gen) {
        super(gen);
    }

    @Override
    protected void generateBlockLootTables() {
        this.dropSelf(BlockRegistrar.WAFFLE);
        this.add(BlockRegistrar.SQUISHED_WAFFLE, BlockLoot::createSlabItemTable);
    }
}
