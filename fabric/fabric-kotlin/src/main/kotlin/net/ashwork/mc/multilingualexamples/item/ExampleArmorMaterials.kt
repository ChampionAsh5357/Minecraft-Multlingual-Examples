/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.item

import net.ashwork.mc.multilingualexamples.ID
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.crafting.Ingredient

/**
 * An enum containing the armor materials for this mod.
 *
 * @param name the name of the material, concatenated with the mod id
 * @param durabilities the armor durabilities for the feet, legs, chest, and head slots respectively
 * @param slotProtections the additive armor attribute modifiers for the feet, legs, chest, and head slots respectively
 * @param enchantmentValue the enchantibility of the armor material
 * @param toughness the additive armor toughness attribute modifier
 * @param knockbackResistance the additive knockback resistance attribute modifier
 * @param sound the armor material equip sound
 * @param repairIngredient a supplied ingredient of what items can repair this armor
 */
enum class ExampleArmorMaterials(name: String, private val durabilities: IntArray,
                                 private val slotProtections: IntArray, private val enchantmentValue: Int,
                                 private val toughness: Float, private val knockbackResistance: Float,
                                 sound: () -> SoundEvent, repairIngredient: () -> Ingredient): ArmorMaterial {
    COLLAGE("collage", 1, intArrayOf(1, 1, 2, 1), 0, ResourceLocation("item.armor.equip_leather"), 0F, 0F, Ingredient::EMPTY);

    /**
     * Default constructor. Provides a durability multiplier to the standard
     * armor base durabilities of 13, 15, 16, and 11 for the feet, legs, chest,
     * and head slots respectively. The sound is provided by its registry name.
     *
     * @param name the name of the material, concatenated with the mod id
     * @param durabilityMultiplier the armor material multiplier for the default durabilities
     * @param slotProtections the additive armor attribute modifiers for the feet, legs, chest, and head slots respectively
     * @param enchantmentValue the enchantibility of the armor material
     * @param soundName the name of the armor material equip sound
     * @param toughness the additive armor toughness attribute modifier
     * @param knockbackResistance the additive knockback resistance attribute modifier
     * @param repairIngredient a supplied ingredient of what items can repair this armor
     */
    constructor(name: String, durabilityMultiplier: Int, slotProtections: IntArray, enchantmentValue: Int,
                soundName: ResourceLocation, toughness: Float, knockbackResistance: Float, repairIngredient: () -> Ingredient):
            this(name, durabilityMultiplier, slotProtections, enchantmentValue, toughness, knockbackResistance,
                { Registry.SOUND_EVENT.get(soundName)!! }, repairIngredient)

    /**
     * Default constructor. Provides a durability multiplier to the standard
     * armor base durabilities of 13, 15, 16, and 11 for the feet, legs, chest,
     * and head slots respectively.
     *
     * @param name the name of the material, concatenated with the mod id
     * @param durabilityMultiplier the armor material multiplier for the default durabilities
     * @param slotProtections the additive armor attribute modifiers for the feet, legs, chest, and head slots respectively
     * @param enchantmentValue the enchantibility of the armor material
     * @param toughness the additive armor toughness attribute modifier
     * @param knockbackResistance the additive knockback resistance attribute modifier
     * @param sound the armor material equip sound
     * @param repairIngredient a supplied ingredient of what items can repair this armor
     */
    constructor(name: String, durabilityMultiplier: Int, slotProtections: IntArray,
                enchantmentValue: Int, toughness: Float, knockbackResistance: Float,
                sound: () -> SoundEvent, repairIngredient: () -> Ingredient):
        this(name, intArrayOf(13 * durabilityMultiplier, 15 * durabilityMultiplier, 16 * durabilityMultiplier, 11 * durabilityMultiplier),
            slotProtections, enchantmentValue, toughness, knockbackResistance, sound, repairIngredient)

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

    override fun getDurabilityForSlot(slot: EquipmentSlot): Int = this.durabilities[slot.index]

    override fun getDefenseForSlot(slot: EquipmentSlot): Int = this.slotProtections[slot.index]

    override fun getEnchantmentValue(): Int = this.enchantmentValue

    override fun getEquipSound(): SoundEvent = this.sound

    override fun getRepairIngredient(): Ingredient = this._repairIngredient

    override fun getName(): String = this._name

    override fun getToughness(): Float = this.toughness

    override fun getKnockbackResistance(): Float = this.knockbackResistance
}
