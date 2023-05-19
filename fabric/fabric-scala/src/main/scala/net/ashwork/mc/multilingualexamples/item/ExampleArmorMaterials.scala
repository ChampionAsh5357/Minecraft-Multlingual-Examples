/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.item

import net.ashwork.mc.multilingualexamples.MultilingualExamples
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.item.{ArmorItem, ArmorMaterial}
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
enum ExampleArmorMaterials(name: String, private val durabilities: Map[ArmorItem.Type, Int],
                           private val typeDefenses: Map[ArmorItem.Type, Int], private val enchantmentValue: Int,
                           private val toughness: Float, private val knockbackResistance: Float,
                           sound: () => SoundEvent, repairIngredient: () => Ingredient) extends ArmorMaterial {
    case COLLAGE extends ExampleArmorMaterials("collage", 1, Map(
        ArmorItem.Type.HELMET -> 1,
        ArmorItem.Type.CHESTPLATE -> 2,
        ArmorItem.Type.LEGGINGS -> 1,
        ArmorItem.Type.BOOTS -> 1
    ), 0, new ResourceLocation("item.armor.equip_leather"), 0F, 0F, () => Ingredient.EMPTY)

    /**
     * Default constructor. Provides a durability multiplier to the standard
     * armor base durabilities of 11, 16, 15, and 13 for the helmet, chestplate,
     * leggings, and boots respectively.
     *
     * @param name                 the name of the material, concatenated with the mod id
     * @param durabilityMultiplier the armor material multiplier for the default durabilities
     * @param typeDefenses         the additive armor attribute modifiers for the armor types
     * @param enchantmentValue     the enchantibility of the armor material
     * @param toughness            the additive armor toughness attribute modifier
     * @param knockbackResistance  the additive knockback resistance attribute modifier
     * @param sound                the armor material equip sound
     * @param repairIngredient     a supplied ingredient of what items can repair this armor
     */
    def this(name: String, durabilityMultiplier: Int, typeDefenses: Map[ArmorItem.Type, Int],
             enchantmentValue: Int, toughness: Float, knockbackResistance: Float,
             sound: () => SoundEvent, repairIngredient: () => Ingredient) =
        this(name, Map(
            ArmorItem.Type.HELMET -> 11 * durabilityMultiplier,
            ArmorItem.Type.CHESTPLATE -> 16 * durabilityMultiplier,
            ArmorItem.Type.LEGGINGS -> 15 * durabilityMultiplier,
            ArmorItem.Type.BOOTS -> 13 * durabilityMultiplier
        ), typeDefenses, enchantmentValue, toughness, knockbackResistance, sound, repairIngredient)

    /**
     * Default constructor. Provides a durability multiplier to the standard
     * armor base durabilities of 11, 16, 15, and 13 for the helmet, chestplate,
     * leggings, and boots respectively. The sound is provided by its registry name.
     *
     * @param name                 the name of the material, concatenated with the mod id
     * @param durabilityMultiplier the armor material multiplier for the default durabilities
     * @param typeDefenses         the additive armor attribute modifiers for the armor types
     * @param enchantmentValue     the enchantibility of the armor material
     * @param soundName            the name of the armor material equip sound
     * @param toughness            the additive armor toughness attribute modifier
     * @param knockbackResistance  the additive knockback resistance attribute modifier
     * @param repairIngredient     a supplied ingredient of what items can repair this armor
     */
    def this(name: String, durabilityMultiplier: Int, typeDefenses: Map[ArmorItem.Type, Int], enchantmentValue: Int,
             soundName: ResourceLocation, toughness: Float, knockbackResistance: Float, repairIngredient: () => Ingredient) =
        this(name, durabilityMultiplier, typeDefenses, enchantmentValue, toughness, knockbackResistance,
            () => BuiltInRegistries.SOUND_EVENT.get(soundName), repairIngredient)

    /*
    All names must have their mod id prefixed. This is used as the default
    armor texture prefix for '*_layer_(1/2)'.
    */
    private val _name: String = s"${MultilingualExamples.ID}:$name"
    /*
    Although sound events are registered before items, there is the off
    chance that this enum gets loaded before registration, so it is better
    to supply a lazy reference instead, preferably through 'RegistryObject'.
    */
    private lazy val _sound: SoundEvent = sound.apply
    /*
    This is made lazy as there is no mechanism to which ingredients are
    handled in this way. No need to be concurrent as this would only be
    called in thread-safe environments.
    */
    private lazy val _repairIngredient: Ingredient = repairIngredient.apply

    override def getDurabilityForType(`type`: ArmorItem.Type): Int = this.durabilities(`type`)

    override def getDefenseForType(`type`: ArmorItem.Type): Int = this.typeDefenses(`type`)

    override def getEnchantmentValue: Int = this.enchantmentValue

    override def getEquipSound: SoundEvent = this._sound

    override def getRepairIngredient: Ingredient = this._repairIngredient

    override def getName: String = _name

    override def getToughness: Float = this.toughness

    override def getKnockbackResistance: Float = this.knockbackResistance
}
