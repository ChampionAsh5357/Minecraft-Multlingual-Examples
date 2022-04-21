/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data;

import net.ashwork.mc.multilingualexamples.MultilingualExamples;
import net.ashwork.mc.multilingualexamples.registrar.ItemRegistrar;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * A data provider which generates item models for the mod.
 */
public final class ItemModels extends ItemModelProvider implements ModelProviderExtension {

    /**
     * A simple constructor.
     *
     * @param gen the generator being written to
     * @param efh a resource holder for linking existing files
     */
    public ItemModels(final DataGenerator gen, final ExistingFileHelper efh) {
        super(gen, MultilingualExamples.ID, efh);
    }

    @Override
    protected void registerModels() {
        this.simpleItem(ItemRegistrar.ASH);
    }

    /**
     * Creates a simple item model through the {@code item/generated} parent.
     *
     * @param item the item whose model is being generated
     */
    private void simpleItem(final Supplier<? extends Item> item) {
        this.simpleItem(item, item.get().getRegistryName());
    }

    /**
     * Creates a simple item model through the {@code item/generated} parent with a single
     * layer.
     *
     * @implNote
     * {@code layer0} should point to a generalized resource location. The item subdirectory
     * transformation is done within this method.
     *
     * @param item the item whose model is being generated
     * @param layer0 the name of the layer0 texture living in {@code textures/item}
     */
    private void simpleItem(final Supplier<? extends Item> item, final ResourceLocation layer0) {
        this.withExistingParent(Objects.requireNonNull(item.get().getRegistryName()).toString(), "item/generated")
                .texture("layer0", this.prefix(layer0, this.folder));
    }
}
