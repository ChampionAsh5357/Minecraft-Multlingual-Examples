/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.item

import net.ashwork.mc.multilingualexamples.ID
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.crafting.Ingredient

/**
 * An enum containing the armor materials for this mod.
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
enum class ExampleArmorMaterials(name: String, private val durabilities: Map<ArmorItem.Type, Int>,
                                 private val typeDefenses: Map<ArmorItem.Type, Int>, private val enchantmentValue: Int,
                                 private val toughness: Float, private val knockbackResistance: Float,
                                 sound: () -> SoundEvent, repairIngredient: () -> Ingredient): ArmorMaterial {
    COLLAGE("collage", 1, mapOf(
        ArmorItem.Type.HELMET to 1,
        ArmorItem.Type.CHESTPLATE to 2,
        ArmorItem.Type.LEGGINGS to 1,
        ArmorItem.Type.BOOTS to 1
    ), 0, ResourceLocation("item.armor.equip_leather"), 0F, 0F, Ingredient::EMPTY);

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
    constructor(name: String, durabilityMultiplier: Int, typeDefenses: Map<ArmorItem.Type, Int>, enchantmentValue: Int,
                soundName: ResourceLocation, toughness: Float, knockbackResistance: Float, repairIngredient: () -> Ingredient):
            this(name, durabilityMultiplier, typeDefenses, enchantmentValue, toughness, knockbackResistance,
                { BuiltInRegistries.SOUND_EVENT.get(soundName)!! }, repairIngredient)

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
    constructor(name: String, durabilityMultiplier: Int, typeDefenses: Map<ArmorItem.Type, Int>,
                enchantmentValue: Int, toughness: Float, knockbackResistance: Float,
                sound: () -> SoundEvent, repairIngredient: () -> Ingredient):
        this(name, mapOf(
            ArmorItem.Type.HELMET to 11 * durabilityMultiplier,
            ArmorItem.Type.CHESTPLATE to 16 * durabilityMultiplier,
            ArmorItem.Type.LEGGINGS to 15 * durabilityMultiplier,
            ArmorItem.Type.BOOTS to 13 * durabilityMultiplier
        ), typeDefenses, enchantmentValue, toughness, knockbackResistance, sound, repairIngredient)

    /*
    All names must have their mod id prefixed. This is used as the default
    armor texture prefix for '*_layer_(1/2)'.
    */
    private val _name: String = "$ID:$name"
    /*
    Although sound events are registered before items, there is the off
    chance that this enum gets loaded before registration, so it is better
    to supply a lazy reference instead, preferably through 'RegistryObject'.
    */
    private val sound: SoundEvent by lazy(sound)
    /*
    This is made lazy as there is no mechanism to which ingredients are
    handled in this way. No need to be concurrent as this would only be
    called in thread-safe environments.
    */
    private val _repairIngredient: Ingredient by lazy(repairIngredient)

    override fun getDurabilityForType(type: ArmorItem.Type): Int = this.durabilities[type]!!

    override fun getDefenseForType(type: ArmorItem.Type): Int = this.typeDefenses[type]!!

    override fun getEnchantmentValue(): Int = this.enchantmentValue

    override fun getEquipSound(): SoundEvent = this.sound

    override fun getRepairIngredient(): Ingredient = this._repairIngredient

    override fun getName(): String = this._name

    override fun getToughness(): Float = this.toughness

    override fun getKnockbackResistance(): Float = this.knockbackResistance
}
