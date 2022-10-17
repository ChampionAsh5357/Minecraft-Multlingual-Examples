package net.ashwork.mc.multilingualexamples.item

import net.ashwork.mc.multilingualexamples.MultilingualExamples
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.crafting.Ingredient
import net.minecraftforge.registries.ForgeRegistries

/**
 * An enum containing the armor materials for this mod.
 */
enum ExampleArmorMaterials implements ArmorMaterial {
    COLLAGE('collage', 1, [1, 1, 2, 1] as int[], 0, new ResourceLocation('item.armor.equip_leather'), 0.0F, 0.0F, { -> Ingredient.EMPTY })

    private final String name
    private final int[] durabilities
    private final int[] slotProtections
    private final int enchantmentValue
    @Lazy
    private SoundEvent _sound
    private final float toughness
    private final float knockbackResistance
    @Lazy
    private Ingredient _repairIngredient

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
    ExampleArmorMaterials(String name, int durabilityMultiplier, int[] slotProtections, int enchantmentValue, ResourceLocation soundName, float toughness, float knockbackResistance, Closure<? extends Ingredient> repairIngredient) {
        this(name, durabilityMultiplier, slotProtections, enchantmentValue, { -> ForgeRegistries.SOUND_EVENTS.getValue(soundName) }, toughness, knockbackResistance, repairIngredient)
    }

    /**
     * Default constructor. Provides a durability multiplier to the standard
     * armor base durabilities of 13, 15, 16, and 11 for the feet, legs, chest,
     * and head slots respectively.
     *
     * @param name the name of the material, concatenated with the mod id
     * @param durabilityMultiplier the armor material multiplier for the default durabilities
     * @param slotProtections the additive armor attribute modifiers for the feet, legs, chest, and head slots respectively
     * @param enchantmentValue the enchantibility of the armor material
     * @param sound the armor material equip sound
     * @param toughness the additive armor toughness attribute modifier
     * @param knockbackResistance the additive knockback resistance attribute modifier
     * @param repairIngredient a supplied ingredient of what items can repair this armor
     */
    ExampleArmorMaterials(String name, int durabilityMultiplier, int[] slotProtections, int enchantmentValue, Closure<? extends SoundEvent> sound, float toughness, float knockbackResistance, Closure<? extends Ingredient> repairIngredient) {
        this(name, [13 * durabilityMultiplier, 15 * durabilityMultiplier, 16 * durabilityMultiplier, 11 * durabilityMultiplier] as int[], slotProtections, enchantmentValue, sound, toughness, knockbackResistance, repairIngredient)
    }

    /**
     * Default constructor.
     *
     * @param name the name of the material, concatenated with the mod id
     * @param durabilities the armor durabilities for the feet, legs, chest, and head slots respectively
     * @param slotProtections the additive armor attribute modifiers for the feet, legs, chest, and head slots respectively
     * @param enchantmentValue the enchantibility of the armor material
     * @param sound the armor material equip sound
     * @param toughness the additive armor toughness attribute modifier
     * @param knockbackResistance the additive knockback resistance attribute modifier
     * @param repairIngredient a supplied ingredient of what items can repair this armor
     */
    ExampleArmorMaterials(String name, int[] durabilities, int[] slotProtections, int enchantmentValue, Closure<? extends SoundEvent> sound, float toughness, float knockbackResistance, Closure<? extends Ingredient> repairIngredient) {
        /*
        All names must have their mod id prefixed. This is used as the default
        armor texture prefix for '*_layer_(1/2)'.
         */
        this.name = '${MultilingualExamples.ID}:${name}'
        this.durabilities = durabilities
        this.slotProtections = slotProtections
        this.enchantmentValue = enchantmentValue
        /*
        Although sound events are registered before items, there is the off
        chance that this enum gets loaded before registration, so it is better
        to supply a lazy reference instead, preferably through 'RegistryObject'.
         */
        this._sound = sound()
        this.toughness = toughness
        this.knockbackResistance = knockbackResistance
        /*
        This is made lazy as there is no mechanism to which ingredients are
        handled in this way. No need to be concurrent as this would only be
        called in thread-safe environments.
         */
        this._repairIngredient = repairIngredient()
    }

    @Override
    int getDurabilityForSlot(EquipmentSlot slot) {
        return this.durabilities[slot.getIndex()]
    }

    @Override
    int getDefenseForSlot(EquipmentSlot slot) {
        return this.slotProtections[slot.getIndex()]
    }

    @Override
    int getEnchantmentValue() {
        return this.enchantmentValue
    }

    @Override
    SoundEvent getEquipSound() {
        return this._sound
    }

    @Override
    Ingredient getRepairIngredient() {
        return this._repairIngredient
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
