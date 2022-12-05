/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data

import net.ashwork.mc.multilingualexamples.registrar.{BlockRegistrar, ItemRegistrar}
import net.minecraft.data.DataGenerator
import net.minecraft.data.recipes.{FinishedRecipe, RecipeProvider, ShapedRecipeBuilder, ShapelessRecipeBuilder, SimpleCookingRecipeBuilder}
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.{Ingredient, RecipeSerializer, SimpleCookingSerializer}
import net.minecraft.world.level.ItemLike
import net.minecraftforge.registries.ForgeRegistries

import java.util.function.Consumer
import scala.jdk.FunctionConverters._

/**
 * A data provider which generates recipes for this mod.
 *
 * @param gen the generator being written to
 */
class ExampleRecipeProvider(gen: DataGenerator) extends RecipeProvider(gen) {

    override def buildCraftingRecipes(writer : Consumer[FinishedRecipe]): Unit = {
        ShapelessRecipeBuilder.shapeless(ItemRegistrar.WAFFLE_MIX.get).requires(Items.EGG, 3)
                .unlockedBy("has_egg", RecipeProvider.has(Items.EGG)).save(writer)
        cookingFood(writer.asScala, ItemRegistrar.WAFFLE_MIX.get, BlockRegistrar.WAFFLE.get, 0.35f, 200)
        ShapedRecipeBuilder.shaped(ItemRegistrar.WAFFLE_CONE.get, 3)
                .pattern("W W")
                .pattern(" W ")
                .define('W', BlockRegistrar.WAFFLE.get)
                .unlockedBy("has_waffle", RecipeProvider.has(BlockRegistrar.WAFFLE.get)).save(writer)
        ShapedRecipeBuilder.shaped(ItemRegistrar.SNOW_CONE.get)
                .pattern("S")
                .pattern("W")
                .define('S', Items.SNOWBALL).define('W', ItemRegistrar.WAFFLE_CONE.get)
                .unlockedBy("has_snowball", RecipeProvider.has(Items.SNOWBALL)).unlockedBy("has_waffle_cone", RecipeProvider.has(ItemRegistrar.WAFFLE_CONE.get)).save(writer)
        ShapedRecipeBuilder.shaped(ItemRegistrar.ICE_CREAM_SANDWICH.get, 6)
                .pattern("WWW")
                .pattern("MMM")
                .pattern("WWW")
                .define('W', BlockRegistrar.SQUISHED_WAFFLE.get).define('M', Items.MILK_BUCKET)
                .unlockedBy("has_milk", RecipeProvider.has(Items.MILK_BUCKET)).unlockedBy("has_squished_waffle", RecipeProvider.has(BlockRegistrar.SQUISHED_WAFFLE.get)).save(writer)
    }

    /**
     * Generates all the cooking recipes for a piece of food. Smoking recipes will
     * be twice as fast while campfire cooking will take three times as long.
     *
     * @param writer      the writer used to generate the recipe
     * @param input       the input being cooked
     * @param output      the output of the cooked input
     * @param experience  the amount of experience to gain after collecting the output
     * @param cookingTime the number of ticks needed for the input to cook
     */
    private def cookingFood(writer: FinishedRecipe => Unit, input: ItemLike, output: ItemLike, experience: Float, cookingTime: Int): Unit = {
        cookingRecipe(writer, "smelting", RecipeSerializer.SMELTING_RECIPE, cookingTime, input, output, experience)
        cookingRecipe(writer, "smoking", RecipeSerializer.SMOKING_RECIPE, cookingTime / 2, input, output, experience)
        cookingRecipe(writer, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING_RECIPE, cookingTime * 3, input, output, experience)
    }

    /**
     * Generates a cooking recipe.
     *
     * This is a copy of [[RecipeProvider.simpleCookingRecipe()]]
     * which fixes the file location for generation of the recipe.
     *
     * @param writer      the writer used to generate the recipe
     * @param type        the suffix of the file denoting how the output is cooked
     * @param serializer  the cooking recipe serializer
     * @param cookingTime the number of ticks needed for the input to cook
     * @param input       the input being cooked
     * @param output      the output of the cooked input
     * @param experience  the amount of experience to gain after collecting the output
     */
    private def cookingRecipe(writer: FinishedRecipe => Unit, `type`: String, serializer: SimpleCookingSerializer[_], cookingTime: Int, input: ItemLike, output: ItemLike, experience: Float): Unit =
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(input), output, experience, cookingTime, serializer)
                .unlockedBy(RecipeProvider.getHasName(input), RecipeProvider.has(input))
                .save(writer.asJavaConsumer, s"${ForgeRegistries.ITEMS.getKey(output.asItem)}_from_${`type`}")
}
