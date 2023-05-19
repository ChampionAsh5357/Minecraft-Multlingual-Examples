/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data

import groovy.transform.CompileStatic
import net.ashwork.mc.multilingualexamples.registrar.BlockRegistrar
import net.ashwork.mc.multilingualexamples.registrar.ItemRegistrar
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.data.recipes.*
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.AbstractCookingRecipe
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.level.ItemLike

import java.util.function.Consumer
/**
 * A data provider which generates recipes for this mod.
 */
@CompileStatic
class ExampleRecipeProvider extends FabricRecipeProvider {

    /**
     * A simple constructor.
     *
     * @param output the output of the data generator
     */
    ExampleRecipeProvider(FabricDataOutput output) {
        super(output)
    }

    @Override
    void buildRecipes(Consumer<FinishedRecipe> exporter) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ItemRegistrar.WAFFLE_MIX).requires(Items.EGG, 3)
                .unlockedBy('has_egg', has(Items.EGG)).save(exporter)
        cookingFood(exporter, ItemRegistrar.WAFFLE_MIX, BlockRegistrar.WAFFLE, 0.35f, 200)
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ItemRegistrar.WAFFLE_CONE, 3)
                .pattern('W W')
                .pattern(' W ')
                .define('W' as char, BlockRegistrar.WAFFLE)
                .unlockedBy('has_waffle', has(BlockRegistrar.WAFFLE)).save(exporter)
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ItemRegistrar.SNOW_CONE)
                .pattern('S')
                .pattern('W')
                .define('S' as char, Items.SNOWBALL).define('W' as char, ItemRegistrar.WAFFLE_CONE)
                .unlockedBy('has_snowball', has(Items.SNOWBALL)).unlockedBy('has_waffle_cone', has(ItemRegistrar.WAFFLE_CONE)).save(exporter)
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ItemRegistrar.ICE_CREAM_SANDWICH, 6)
                .pattern('WWW')
                .pattern('MMM')
                .pattern('WWW')
                .define('W' as char, BlockRegistrar.SQUISHED_WAFFLE).define('M' as char, Items.MILK_BUCKET)
                .unlockedBy('has_milk', has(Items.MILK_BUCKET)).unlockedBy('has_squished_waffle', has(BlockRegistrar.SQUISHED_WAFFLE)).save(exporter)
    }

    /**
     * Generates all the cooking recipes for a piece of food. Smoking recipes will
     * be twice as fast while campfire cooking will take three times as long.
     *
     * @param writer the writer used to generate the recipe
     * @param input the input being cooked
     * @param output the output of the cooked {@code input}
     * @param experience the amount of experience to gain after collecting the {@code output}
     * @param cookingTime the number of ticks needed for the {@code input} to cook
     */
    private static void cookingFood(Consumer<FinishedRecipe> writer, ItemLike input, ItemLike output, float experience, int cookingTime) {
        cookingRecipe(writer, 'smelting', RecipeSerializer.SMELTING_RECIPE, cookingTime, input, output, experience)
        cookingRecipe(writer, 'smoking', RecipeSerializer.SMOKING_RECIPE, (cookingTime / 2) as int, input, output, experience)
        cookingRecipe(writer, 'campfire_cooking', RecipeSerializer.CAMPFIRE_COOKING_RECIPE, cookingTime * 3, input, output, experience)
    }

    /**
     * Generates a cooking recipe. This is a copy of {@link #simpleCookingRecipe(java.util.function.Consumer, java.lang.String, net.minecraft.world.item.crafting.RecipeSerializer, int, net.minecraft.world.level.ItemLike, net.minecraft.world.level.ItemLike, float)}
     * which fixes the file location for generation of the recipe.
     *
     * @param writer the writer used to generate the recipe
     * @param type the suffix of the file denoting how the {@code output} is cooked
     * @param serializer the cooking recipe serializer
     * @param cookingTime the number of ticks needed for the {@code input} to cook
     * @param input the input being cooked
     * @param output the output of the cooked {@code input}
     * @param experience the amount of experience to gain after collecting the {@code output}
     */
    private static void cookingRecipe(Consumer<FinishedRecipe> writer, String type, RecipeSerializer<? extends AbstractCookingRecipe> serializer, int cookingTime, ItemLike input, ItemLike output, float experience) {
        SimpleCookingRecipeBuilder.generic(Ingredient.of(input), RecipeCategory.FOOD, output, experience, cookingTime, serializer).unlockedBy(getHasName(input), has(input)).save(writer, "${BuiltInRegistries.ITEM.getKey(output.asItem())}_from_$type")
    }
}
