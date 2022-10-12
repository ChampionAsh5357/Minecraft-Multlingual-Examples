package net.ashwork.mc.multilingualexamples.item;

import net.ashwork.mc.multilingualexamples.client.MultilingualExamplesClient;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.fml.DistExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

//TODO: Document
public class CustomArmorModelItem extends ArmorItem {

    public CustomArmorModelItem(ArmorMaterial material, EquipmentSlot slot, Properties properties) {
        super(material, slot, properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @NotNull Model getGenericArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> original) {
                return DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> MultilingualExamplesClient.instance().armorModelManager().getArmorModel(CustomArmorModelItem.this.material, entity, stack, slot, original));
            }
        });
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> MultilingualExamplesClient.instance().armorModelManager().getTexture(CustomArmorModelItem.this.material, stack, entity, slot, type));
    }
}
