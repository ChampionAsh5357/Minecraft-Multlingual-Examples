package net.ashwork.mc.multilingualexamples.data

import net.ashwork.mc.multilingualexamples.registrar.*
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.core.Registry
import net.minecraft.data.recipes.*
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.SimpleCookingSerializer
import net.minecraft.world.level.ItemLike
import java.util.function.Consumer

/**
 * A data provider which generates recipes for this mod.
 * 
 * @param gen the generator being written to
 */
class ExampleRecipeProvider(gen: FabricDataGenerator): FabricRecipeProvider(gen) {
    
    override fun generateRecipes(exporter: Consumer<FinishedRecipe>) {
        ShapelessRecipeBuilder.shapeless(WAFFLE_MIX).requires(Items.EGG, 3)
            .unlockedBy("has_egg", RecipeProvider.has(Items.EGG)).save(exporter)
        cookingFood(WAFFLE_MIX, WAFFLE, 0.35f, 200) { exporter.accept(it) }
        ShapedRecipeBuilder.shaped(WAFFLE_CONE, 3)
            .pattern("W W")
            .pattern(" W ")
            .define('W', WAFFLE)
            .unlockedBy("has_waffle", RecipeProvider.has(WAFFLE)).save(exporter)
        ShapedRecipeBuilder.shaped(SNOW_CONE)
            .pattern("S")
            .pattern("W")
            .define('S', Items.SNOWBALL).define('W', WAFFLE_CONE)
            .unlockedBy("has_snowball", RecipeProvider.has(Items.SNOWBALL)).unlockedBy("has_waffle_cone",
                RecipeProvider.has(WAFFLE_CONE)
            ).save(exporter)
        ShapedRecipeBuilder.shaped(ICE_CREAM_SANDWICH, 6)
            .pattern("WWW")
            .pattern("MMM")
            .pattern("WWW")
            .define('W', SQUISHED_WAFFLE).define('M', Items.MILK_BUCKET)
            .unlockedBy("has_milk", RecipeProvider.has(Items.MILK_BUCKET)).unlockedBy("has_squished_waffle",
                RecipeProvider.has(SQUISHED_WAFFLE)
            ).save(exporter)
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
            .unlockedBy(RecipeProvider.getHasName(input), RecipeProvider.has(input))
            .save(writer, "${Registry.ITEM.getKey(output.asItem())}_from_$type")
}
