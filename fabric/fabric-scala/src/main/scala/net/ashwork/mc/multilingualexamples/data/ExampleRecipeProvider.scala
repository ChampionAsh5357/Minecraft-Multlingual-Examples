/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.data

import net.ashwork.mc.multilingualexamples.registrar.{BlockRegistrar, ItemRegistrar}
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.data.recipes.{FinishedRecipe, RecipeCategory, RecipeProvider, ShapedRecipeBuilder, ShapelessRecipeBuilder, SimpleCookingRecipeBuilder}
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.{AbstractCookingRecipe, Ingredient, RecipeSerializer, SimpleCookingSerializer}
import net.minecraft.world.level.ItemLike

import java.util.function.Consumer
import scala.jdk.FunctionConverters.*

/**
 * A data provider which generates recipes for this mod.
 *
 * @param output the output of the data generator
 */
class ExampleRecipeProvider(output: FabricDataOutput) extends FabricRecipeProvider(output)  {

    override def buildRecipes(exporter: Consumer[FinishedRecipe]): Unit = {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ItemRegistrar.WAFFLE_MIX).requires(Items.EGG, 3)
                .unlockedBy("has_egg", RecipeProvider.has(Items.EGG)).save(exporter)
        cookingFood(exporter.asScala, ItemRegistrar.WAFFLE_MIX, BlockRegistrar.WAFFLE, 0.35f, 200)
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ItemRegistrar.WAFFLE_CONE, 3)
                .pattern("W W")
                .pattern(" W ")
                .define('W', BlockRegistrar.WAFFLE)
                .unlockedBy("has_waffle", RecipeProvider.has(BlockRegistrar.WAFFLE)).save(exporter)
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ItemRegistrar.SNOW_CONE)
                .pattern("S")
                .pattern("W")
                .define('S', Items.SNOWBALL).define('W', ItemRegistrar.WAFFLE_CONE)
                .unlockedBy("has_snowball", RecipeProvider.has(Items.SNOWBALL)).unlockedBy("has_waffle_cone", RecipeProvider.has(ItemRegistrar.WAFFLE_CONE)).save(exporter)
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ItemRegistrar.ICE_CREAM_SANDWICH, 6)
                .pattern("WWW")
                .pattern("MMM")
                .pattern("WWW")
                .define('W', BlockRegistrar.SQUISHED_WAFFLE).define('M', Items.MILK_BUCKET)
                .unlockedBy("has_milk", RecipeProvider.has(Items.MILK_BUCKET)).unlockedBy("has_squished_waffle", RecipeProvider.has(BlockRegistrar.SQUISHED_WAFFLE)).save(exporter)
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
     * Generates a cooking recipe. This is a copy of [[RecipeProvider.simpleCookingRecipe]]
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
    private def cookingRecipe(writer: FinishedRecipe => Unit, `type`: String, serializer: RecipeSerializer[_ <: AbstractCookingRecipe], cookingTime: Int, input: ItemLike, output: ItemLike, experience: Float): Unit =
        SimpleCookingRecipeBuilder.generic(Ingredient.of(input), RecipeCategory.FOOD, output, experience, cookingTime, serializer)
                .unlockedBy(RecipeProvider.getHasName(input), RecipeProvider.has(input))
                .save(writer.asJavaConsumer, s"${BuiltInRegistries.ITEM.getKey(output.asItem)}_from_${`type`}")
}
