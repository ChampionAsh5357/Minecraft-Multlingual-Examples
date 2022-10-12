/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.item

import net.ashwork.mc.multilingualexamples.client.clientInstance
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
import net.minecraftforge.fml.DistExecutor
import java.util.concurrent.Callable
import java.util.function.Consumer

/**
 * A generic armor item class to use custom armor models managed by the
 * [ArmorModelManager].
 *
 * @param material the material of the armor
 * @param slot the slot the armor can be worn in
 * @param properties the item properties
 */
open class CustomArmorModelItem(material: ArmorMaterial, slot: EquipmentSlot, properties: Properties):
    ArmorItem(material, slot, properties) {

    override fun initializeClient(consumer: Consumer<IClientItemExtensions>) {
        consumer.accept(object: IClientItemExtensions {
            override fun getGenericArmorModel(entity: LivingEntity, stack: ItemStack, slot: EquipmentSlot, original: HumanoidModel<*>): Model {
                // Check sides in case of illegal calling
                return DistExecutor.unsafeCallWhenOn(Dist.CLIENT) {
                    Callable {
                        clientInstance().armorModelManager().getArmorModel(material, entity, stack, slot, original)
                    }
                }
            }
        })
    }

    override fun getArmorTexture(stack: ItemStack, entity: Entity, slot: EquipmentSlot, type: String?): String? {
        // Check sides in case of illegal calling
        return DistExecutor.unsafeCallWhenOn(Dist.CLIENT) {
            Callable {
                clientInstance().armorModelManager().getTexture(this.material, stack, entity, slot, type)
            }
        }
    }
}
