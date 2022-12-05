/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data

import net.ashwork.mc.multilingualexamples.registrar.*
import net.minecraft.data.DataGenerator
import net.minecraft.data.recipes.*
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.SimpleCookingSerializer
import net.minecraft.world.level.ItemLike
import net.minecraftforge.registries.ForgeRegistries
import java.util.function.Consumer

/**
 * A data provider which generates recipes for this mod.
 *
 * @param gen the generator being written to
 */
class ExampleRecipeProvider(gen: DataGenerator): RecipeProvider(gen) {

    override fun buildCraftingRecipes(writer: Consumer<FinishedRecipe>) {
        ShapelessRecipeBuilder.shapeless(WAFFLE_MIX.get()).requires(Items.EGG, 3)
            .unlockedBy("has_egg", has(Items.EGG)).save(writer)
        cookingFood(WAFFLE_MIX.get(), WAFFLE.get(), 0.35f, 200) { writer.accept(it) }
        ShapedRecipeBuilder.shaped(WAFFLE_CONE.get(), 3)
            .pattern("W W")
            .pattern(" W ")
            .define('W', WAFFLE.get())
            .unlockedBy("has_waffle", has(WAFFLE.get())).save(writer)
        ShapedRecipeBuilder.shaped(SNOW_CONE.get())
            .pattern("S")
            .pattern("W")
            .define('S', Items.SNOWBALL).define('W', WAFFLE_CONE.get())
            .unlockedBy("has_snowball", has(Items.SNOWBALL)).unlockedBy("has_waffle_cone", has(WAFFLE_CONE.get())).save(writer)
        ShapedRecipeBuilder.shaped(ICE_CREAM_SANDWICH.get(), 6)
            .pattern("WWW")
            .pattern("MMM")
            .pattern("WWW")
            .define('W', SQUISHED_WAFFLE.get()).define('M', Items.MILK_BUCKET)
            .unlockedBy("has_milk", has(Items.MILK_BUCKET)).unlockedBy("has_squished_waffle", has(SQUISHED_WAFFLE.get())).save(writer)
    }

    /**
     * Generates all the cooking recipes for a piece of food. Smoking recipes will
     * be twice as fast while campfire cooking will take three times as long.
     *
     * @param writer the writer used to generate the recipe
     * @param input the input being cooked
     * @param output the output of the cooked [input]
     * @param experience the amount of experience to gain after collecting the [output]
     * @param cookingTime the number of ticks needed for the [input] to cook
     */
    private fun cookingFood(input: ItemLike, output: ItemLike, experience: Float, cookingTime: Int, writer: (FinishedRecipe) -> Unit) {
        cookingRecipe("smelting", RecipeSerializer.SMELTING_RECIPE, cookingTime, input, output, experience, writer)
        cookingRecipe("smoking", RecipeSerializer.SMOKING_RECIPE, cookingTime / 2, input, output, experience, writer)
        cookingRecipe("campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING_RECIPE, cookingTime * 3, input, output, experience, writer)
    }

    /**
     * Generates a cooking recipe.
     *
     * This is a copy of [RecipeProvider.simpleCookingRecipe]
     * which fixes the file location for generation of the recipe.
     *
     * @param writer the writer used to generate the recipe
     * @param type the suffix of the file denoting how the [output] is cooked
     * @param serializer the cooking recipe serializer
     * @param cookingTime the number of ticks needed for the [input] to cook
     * @param input the input being cooked
     * @param output the output of the cooked [input]
     * @param experience the amount of experience to gain after collecting the [output]
     */
    private fun cookingRecipe(type: String, serializer: SimpleCookingSerializer<*>, cookingTime: Int, input: ItemLike, output: ItemLike, experience: Float, writer: (FinishedRecipe) -> Unit) =
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(input), output, experience, cookingTime, serializer)
            .unlockedBy(getHasName(input), has(input))
            .save(writer, "${ForgeRegistries.ITEMS.getKey(output.asItem())}_from_$type")
}
