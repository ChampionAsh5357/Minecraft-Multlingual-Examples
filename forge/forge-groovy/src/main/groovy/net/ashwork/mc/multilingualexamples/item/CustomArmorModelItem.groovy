package net.ashwork.mc.multilingualexamples.item

import net.ashwork.mc.multilingualexamples.client.MultilingualExamplesClient
import net.ashwork.mc.multilingualexamples.client.model.ArmorModelManager
import net.minecraft.client.model.HumanoidModel
import net.minecraft.client.model.Model
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.ItemStack
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.extensions.common.IClientItemExtensions
import net.minecraftforge.fml.loading.FMLEnvironment
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

import java.util.function.Consumer
/**
 * A generic armor item class to use custom armor models managed by the
 * {@link ArmorModelManager}.
 */
class CustomArmorModelItem extends ArmorItem {

    /**
     * Default constructor.
     *
     * @param material the material of the armor
     * @param slot the slot the armor can be worn in
     * @param properties the item properties
     */
    CustomArmorModelItem(ArmorMaterial material, EquipmentSlot slot, Properties properties) {
        super(material, slot, properties)
    }

    @Override
    void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            @NotNull Model getGenericArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlot slot, HumanoidModel<? extends LivingEntity> original) {
                // Check sides in case of illegal calling
                return FMLEnvironment.dist == Dist.CLIENT ? MultilingualExamplesClient.instance().armorModelManager().getArmorModel(CustomArmorModelItem.this.material, entity, stack, slot, original)
                        : super.getGenericArmorModel(entity, stack, slot, original)
            }
        })
    }

    @Override
    @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        // Check sides in case of illegal calling
        return FMLEnvironment.dist == Dist.CLIENT ? MultilingualExamplesClient.instance().armorModelManager().getTexture(this.material, stack, entity, slot, type)
                : super.getArmorTexture(stack, entity, slot, type)
    }
}
