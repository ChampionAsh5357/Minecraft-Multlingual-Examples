package net.ashwork.mc.multilingualexamples.item;

import net.ashwork.mc.multilingualexamples.MultilingualExamples;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

//TODO: Document
@MethodsReturnNonnullByDefault
public enum ExampleArmorMaterials implements ArmorMaterial {
    COLLAGE("collage", 1, new int[]{1, 1, 2, 1}, 10, new ResourceLocation("item.armor.equip_leather"), 0.0F, 0.0F, () -> Ingredient.EMPTY);
    private final String name;
    private final int[] durabilities;
    private final int[] slotProtections;
    private final int enchantmentValue;
    private final Supplier<? extends SoundEvent> sound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<? extends Ingredient> repairIngredient;

    ExampleArmorMaterials(String name, int durabilityMultiplier, int[] slotProtections, int enchantmentValue, ResourceLocation soundName, float toughness, float knockbackResistance, Supplier<? extends Ingredient> repairIngredient) {
        this(name, durabilityMultiplier, slotProtections, enchantmentValue, RegistryObject.create(soundName, ForgeRegistries.SOUND_EVENTS), toughness, knockbackResistance, repairIngredient);
    }

    ExampleArmorMaterials(String name, int durabilityMultiplier, int[] slotProtections, int enchantmentValue, Supplier<? extends SoundEvent> sound, float toughness, float knockbackResistance, Supplier<? extends Ingredient> repairIngredient) {
        this(name, new int[]{13 * durabilityMultiplier, 15 * durabilityMultiplier, 16 * durabilityMultiplier, 11 * durabilityMultiplier}, slotProtections, enchantmentValue, sound, toughness, knockbackResistance, repairIngredient);
    }

    ExampleArmorMaterials(String name, int[] durabilities, int[] slotProtections, int enchantmentValue, Supplier<? extends SoundEvent> sound, float toughness, float knockbackResistance, Supplier<? extends Ingredient> repairIngredient) {
        this.name = MultilingualExamples.ID + ":" + name;
        this.durabilities = durabilities;
        this.slotProtections = slotProtections;
        this.enchantmentValue = enchantmentValue;
        this.sound = sound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = Lazy.of(repairIngredient);
    }

    @Override
    public int getDurabilityForSlot(EquipmentSlot slot) {
        return this.durabilities[slot.getIndex()];
    }

    @Override
    public int getDefenseForSlot(EquipmentSlot slot) {
        return this.slotProtections[slot.getIndex()];
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
