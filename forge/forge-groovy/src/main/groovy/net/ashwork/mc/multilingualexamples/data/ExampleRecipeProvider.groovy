/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data

import net.ashwork.mc.multilingualexamples.registrar.BlockRegistrar
import net.ashwork.mc.multilingualexamples.registrar.ItemRegistrar
import net.minecraft.data.DataGenerator
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.data.recipes.RecipeProvider
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.data.recipes.ShapelessRecipeBuilder
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.AbstractCookingRecipe
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.SimpleCookingSerializer
import net.minecraft.world.level.ItemLike
import net.minecraftforge.registries.ForgeRegistries

import java.util.function.Consumer

/**
 * A data provider which generates recipes for this mod.
 */
class ExampleRecipeProvider extends RecipeProvider {

    /**
     * Default constructor.
     *
     * @param gen the generator being written to
     */
    ExampleRecipeProvider(DataGenerator gen) {
        super(gen)
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> writer) {
        ShapelessRecipeBuilder.shapeless(ItemRegistrar.WAFFLE_MIX.get()).requires(Items.EGG, 3)
                .unlockedBy('has_egg', has(Items.EGG)).save(writer)
        cookingFood(writer, ItemRegistrar.WAFFLE_MIX.get(), BlockRegistrar.WAFFLE.get(), 0.35f, 200)
        ShapedRecipeBuilder.shaped(ItemRegistrar.WAFFLE_CONE.get(), 3)
                .pattern('W W')
                .pattern(' W ')
                .define('W' as char, BlockRegistrar.WAFFLE.get())
                .unlockedBy('has_waffle', has(BlockRegistrar.WAFFLE.get())).save(writer)
        ShapedRecipeBuilder.shaped(ItemRegistrar.SNOW_CONE.get())
                .pattern('S')
                .pattern('W')
                .define('S' as char, Items.SNOWBALL).define('W' as char, ItemRegistrar.WAFFLE_CONE.get())
                .unlockedBy('has_snowball', has(Items.SNOWBALL)).unlockedBy('has_waffle_cone', has(ItemRegistrar.WAFFLE_CONE.get())).save(writer)
        ShapedRecipeBuilder.shaped(ItemRegistrar.ICE_CREAM_SANDWICH.get(), 6)
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
     * Generates a cooking recipe.
     *
     * This is a copy of {@link #simpleCookingRecipe(Consumer, String, SimpleCookingSerializer, int, ItemLike, ItemLike, float)}
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
    private static void cookingRecipe(Consumer<FinishedRecipe> writer, String type, SimpleCookingSerializer<? extends AbstractCookingRecipe> serializer, int cookingTime, ItemLike input, ItemLike output, float experience) {
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(input), output, experience, cookingTime, serializer).unlockedBy(getHasName(input), has(input)).save(writer, "${ForgeRegistries.ITEMS.getKey(output.asItem())}_from_$type")
    }
}
