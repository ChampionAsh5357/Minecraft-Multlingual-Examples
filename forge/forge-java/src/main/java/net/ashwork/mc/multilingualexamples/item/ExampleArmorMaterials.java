/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.item;

import net.ashwork.mc.multilingualexamples.MultilingualExamples;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.function.Supplier;

/**
 * An enum containing the armor materials for this mod.
 */
@MethodsReturnNonnullByDefault
public enum ExampleArmorMaterials implements ArmorMaterial {
    COLLAGE("collage", 1, Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.HELMET, 1);
        map.put(ArmorItem.Type.CHESTPLATE, 2);
        map.put(ArmorItem.Type.LEGGINGS, 1);
        map.put(ArmorItem.Type.BOOTS, 1);
    }), 0, new ResourceLocation("item.armor.equip_leather"), 0.0F, 0.0F, () -> Ingredient.EMPTY);
    private final String name;
    private final EnumMap<ArmorItem.Type, Integer> durabilities;
    private final EnumMap<ArmorItem.Type, Integer> typeDefenses;
    private final int enchantmentValue;
    private final Supplier<? extends SoundEvent> sound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<? extends Ingredient> repairIngredient;

    /**
     * Default constructor. Provides a durability multiplier to the standard
     * armor base durabilities of 11, 16, 15, and 13 for the head, chest, legs,
     * and feet slots respectively. The sound is provided by its registry name.
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
    ExampleArmorMaterials(String name, int durabilityMultiplier, EnumMap<ArmorItem.Type, Integer> typeDefenses, int enchantmentValue, ResourceLocation soundName, float toughness, float knockbackResistance, Supplier<? extends Ingredient> repairIngredient) {
        this(name, durabilityMultiplier, typeDefenses, enchantmentValue, () -> ForgeRegistries.SOUND_EVENTS.getValue(soundName), toughness, knockbackResistance, repairIngredient);
    }

    /**
     * Default constructor. Provides a durability multiplier to the standard
     * armor base durabilities of 11, 16, 15, and 13 for the head, chest, legs,
     * and feet slots respectively.
     *
     * @param name the name of the material, concatenated with the mod id
     * @param durabilityMultiplier the armor material multiplier for the default durabilities
     * @param typeDefenses the additive armor attribute modifiers for the armor types
     * @param enchantmentValue the enchantibility of the armor material
     * @param sound the armor material equip sound
     * @param toughness the additive armor toughness attribute modifier
     * @param knockbackResistance the additive knockback resistance attribute modifier
     * @param repairIngredient a supplied ingredient of what items can repair this armor
     */
    ExampleArmorMaterials(String name, int durabilityMultiplier, EnumMap<ArmorItem.Type, Integer> typeDefenses, int enchantmentValue, Supplier<? extends SoundEvent> sound, float toughness, float knockbackResistance, Supplier<? extends Ingredient> repairIngredient) {
        this(name, Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
            map.put(ArmorItem.Type.HELMET, 11 * durabilityMultiplier);
            map.put(ArmorItem.Type.CHESTPLATE, 16 * durabilityMultiplier);
            map.put(ArmorItem.Type.LEGGINGS, 15 * durabilityMultiplier);
            map.put(ArmorItem.Type.BOOTS, 13 * durabilityMultiplier);
        }), typeDefenses, enchantmentValue, sound, toughness, knockbackResistance, repairIngredient);
    }

    /**
     * Default constructor.
     *
     * @param name the name of the material, concatenated with the mod id
     * @param durabilities the armor durabilities for the armor types
     * @param typeDefenses the additive armor attribute modifiers for the armor types
     * @param enchantmentValue the enchantibility of the armor material
     * @param sound the armor material equip sound
     * @param toughness the additive armor toughness attribute modifier
     * @param knockbackResistance the additive knockback resistance attribute modifier
     * @param repairIngredient a supplied ingredient of what items can repair this armor
     */
    ExampleArmorMaterials(String name, EnumMap<ArmorItem.Type, Integer> durabilities, EnumMap<ArmorItem.Type, Integer> typeDefenses, int enchantmentValue, Supplier<? extends SoundEvent> sound, float toughness, float knockbackResistance, Supplier<? extends Ingredient> repairIngredient) {
        /*
        All names must have their mod id prefixed. This is used as the default
        armor texture prefix for '*_layer_(1/2)'.
         */
        this.name = MultilingualExamples.ID + ":" + name;
        this.durabilities = durabilities;
        this.typeDefenses = typeDefenses;
        this.enchantmentValue = enchantmentValue;
        /*
        Although sound events are registered before items, there is the off
        chance that this enum gets loaded before registration, so it is better
        to supply a lazy reference instead, preferably through 'RegistryObject'.
         */
        this.sound = Lazy.of(sound);
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        /*
        This is made lazy as there is no mechanism to which ingredients are
        handled in this way. No need to be concurrent as this would only be
        called in thread-safe environments.
         */
        this.repairIngredient = Lazy.of(repairIngredient);
    }

    @Override
    public int getDurabilityForType(@NotNull ArmorItem.Type type) {
        return this.durabilities.get(type);
    }

    @Override
    public int getDefenseForType(@NotNull ArmorItem.Type type) {
        return this.typeDefenses.get(type);
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.sound.get();
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
