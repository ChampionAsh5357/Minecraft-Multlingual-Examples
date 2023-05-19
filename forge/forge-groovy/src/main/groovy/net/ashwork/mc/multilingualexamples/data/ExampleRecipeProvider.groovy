/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data

import groovy.transform.CompileStatic
import net.ashwork.mc.multilingualexamples.registrar.BlockRegistrar
import net.ashwork.mc.multilingualexamples.registrar.ItemRegistrar
import net.minecraft.data.PackOutput
import net.minecraft.data.recipes.*
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.AbstractCookingRecipe
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.level.ItemLike
import net.minecraftforge.registries.ForgeRegistries

import java.util.function.Consumer
/**
 * A data provider which generates recipes for this mod.
 */
@CompileStatic
class ExampleRecipeProvider extends RecipeProvider {

    /**
     * Default constructor.
     *
     * @param output the output of the data generator
     */
    ExampleRecipeProvider(PackOutput output) {
        super(output)
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> writer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ItemRegistrar.WAFFLE_MIX.get()).requires(Items.EGG, 3)
                .unlockedBy('has_egg', has(Items.EGG)).save(writer)
        cookingFood(writer, ItemRegistrar.WAFFLE_MIX.get(), BlockRegistrar.WAFFLE.get(), 0.35f, 200)
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ItemRegistrar.WAFFLE_CONE.get(), 3)
                .pattern('W W')
                .pattern(' W ')
                .define('W' as char, BlockRegistrar.WAFFLE.get())
                .unlockedBy('has_waffle', has(BlockRegistrar.WAFFLE.get())).save(writer)
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ItemRegistrar.SNOW_CONE.get())
                .pattern('S')
                .pattern('W')
                .define('S' as char, Items.SNOWBALL).define('W' as char, ItemRegistrar.WAFFLE_CONE.get())
                .unlockedBy('has_snowball', has(Items.SNOWBALL)).unlockedBy('has_waffle_cone', has(ItemRegistrar.WAFFLE_CONE.get())).save(writer)
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ItemRegistrar.ICE_CREAM_SANDWICH.get(), 6)
                .pattern('WWW')
                .pattern('MMM')
                .pattern('WWW')
                .define('W' as char, BlockRegistrar.SQUISHED_WAFFLE.get()).define('M' as char, Items.MILK_BUCKET)
                .unlockedBy('has_milk', has(Items.MILK_BUCKET)).unlockedBy('has_squished_waffle', has(BlockRegistrar.SQUISHED_WAFFLE.get())).save(writer)
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
        SimpleCookingRecipeBuilder.generic(Ingredient.of(input), RecipeCategory.FOOD, output, experience, cookingTime, serializer).unlockedBy(getHasName(input), has(input)).save(writer, "${ForgeRegistries.ITEMS.getKey(output.asItem())}_from_$type")
    }
}
