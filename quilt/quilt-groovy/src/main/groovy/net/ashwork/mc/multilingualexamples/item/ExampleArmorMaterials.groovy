/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.item

import groovy.transform.CompileStatic
import net.ashwork.mc.multilingualexamples.MultilingualExamples
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.crafting.Ingredient
/**
 * An enum containing the armor materials for this mod.
 */
@CompileStatic
enum ExampleArmorMaterials implements ArmorMaterial {
    COLLAGE('collage', 1, [(ArmorItem.Type.HELMET): 1, (ArmorItem.Type.CHESTPLATE): 2, (ArmorItem.Type.LEGGINGS): 1, (ArmorItem.Type.BOOTS): 1], 0, new ResourceLocation('item.armor.equip_leather'), 0.0F, 0.0F, { -> Ingredient.EMPTY })

    private final String name
    private final Map<ArmorItem.Type, Integer> durabilities
    private final Map<ArmorItem.Type, Integer> typeDefenses
    private final int enchantmentValue
    private final Closure<? extends SoundEvent> sound
    private final float toughness
    private final float knockbackResistance
    private final Closure<? extends Ingredient> _repairIngredient

    /**
     * Default constructor. Provides a durability multiplier to the standard
     * armor base durabilities of 11, 16, 15, and 13 for the helmet, chestplate,
     * leggings, and boots respectively. The sound is provided by its registry name.
     *
     * @param name the name of the material, concatenated with the mod id
     * @param durabilityMultiplier the armor material multiplier for the default durabilities
     * @param typeDefenses the additive armor attribute modifiers for the armor types
     * @param enchantmentValue the enchantibility of the armor material
     * @param soundName the name of the armor material equip sound
     * @param toughness the additive armor toughness attribute modifier
     * @param knockbackResistance the additive knockback resistance attribute modifier
     * @param repairIngredient a supplied ingredient of what items can repair this armor
     */
    ExampleArmorMaterials(String name, int durabilityMultiplier, Map<ArmorItem.Type, Integer> typeDefenses, int enchantmentValue, ResourceLocation soundName, float toughness, float knockbackResistance, Closure<? extends Ingredient> repairIngredient) {
        this(name, durabilityMultiplier, typeDefenses, enchantmentValue, { -> BuiltInRegistries.SOUND_EVENT.get(soundName) }, toughness, knockbackResistance, repairIngredient)
    }

    /**
     * Default constructor. Provides a durability multiplier to the standard
     * armor base durabilities of 11, 16, 15, and 13 for the helmet, chestplate,
     * leggings, and boots respectively.
     *
     * @param name the name of the material, concatenated with the mod id
     * @param durabilityMultiplier the armor material multiplier for the default durabilities
     * @param typeDefenses the additive armor attribute modifiers for the armor types
     * @param enchantmentValue the enchantibility of the armor material
     * @param toughness the additive armor toughness attribute modifier
     * @param knockbackResistance the additive knockback resistance attribute modifier
     * @param sound the armor material equip sound
     * @param repairIngredient a supplied ingredient of what items can repair this armor
     */
    ExampleArmorMaterials(String name, int durabilityMultiplier, Map<ArmorItem.Type, Integer> typeDefenses, int enchantmentValue, Closure<? extends SoundEvent> sound, float toughness, float knockbackResistance, Closure<? extends Ingredient> repairIngredient) {
        this(name, [(ArmorItem.Type.HELMET): 11 * durabilityMultiplier, (ArmorItem.Type.CHESTPLATE): 16 * durabilityMultiplier, (ArmorItem.Type.LEGGINGS): 15 * durabilityMultiplier, (ArmorItem.Type.BOOTS): 13 * durabilityMultiplier], typeDefenses, enchantmentValue, sound, toughness, knockbackResistance, repairIngredient)
    }

    /**
     * Default constructor.
     *
     * @param name the name of the material, concatenated with the mod id
     * @param durabilities the armor durabilities for the armor types
     * @param typeDefenses the additive armor attribute modifiers for the armor types
     * @param enchantmentValue the enchantibility of the armor material
     * @param toughness the additive armor toughness attribute modifier
     * @param knockbackResistance the additive knockback resistance attribute modifier
     * @param sound the armor material equip sound
     * @param repairIngredient a supplied ingredient of what items can repair this armor
     */
    ExampleArmorMaterials(String name, Map<ArmorItem.Type, Integer> durabilities, Map<ArmorItem.Type, Integer> typeDefenses, int enchantmentValue, Closure<? extends SoundEvent> sound, float toughness, float knockbackResistance, Closure<? extends Ingredient> repairIngredient) {
        /*
        All names must have their mod id prefixed. This is used as the default
        armor texture prefix for '*_layer_(1/2)'.
         */
        this.name = "${MultilingualExamples.id()}:$name"
        this.durabilities = durabilities
        this.typeDefenses = typeDefenses
        this.enchantmentValue = enchantmentValue
        /*
        Although sound events are registered before items, there is the off
        chance that this enum gets loaded before registration, so it is better
        to supply a lazy reference instead, preferably through 'RegistryObject'.
         */
        this.sound = sound.memoize()
        this.toughness = toughness
        this.knockbackResistance = knockbackResistance
        /*
        This is made lazy as there is no mechanism to which ingredients are
        handled in this way. No need to be concurrent as this would only be
        called in thread-safe environments.
         */
        this._repairIngredient = repairIngredient.memoize()
    }

    @Override
    int getDurabilityForType(ArmorItem.Type type) {
        return this.durabilities[type]
    }

    @Override
    int getDefenseForType(ArmorItem.Type type) {
        return this.typeDefenses[type]
    }

    @Override
    int getEnchantmentValue() {
        return this.enchantmentValue
    }

    @Override
    SoundEvent getEquipSound() {
        return this.sound()
    }

    @Override
    Ingredient getRepairIngredient() {
        return this._repairIngredient()
    }

    @Override
    String getName() {
        return this.name
    }

    @Override
    float getToughness() {
        return this.toughness
    }

    @Override
    float getKnockbackResistance() {
        return this.knockbackResistance
    }
}
