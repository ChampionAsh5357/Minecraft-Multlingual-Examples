/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.item

import net.ashwork.mc.multilingualexamples.client.MultilingualExamplesClient
import net.ashwork.mc.multilingualexamples.client.model.ArmorModelManager
import net.minecraft.client.model.{HumanoidModel, Model}
import net.minecraft.world.entity.{Entity, EquipmentSlot, LivingEntity}
import net.minecraft.world.item.{ArmorItem, ArmorMaterial, ItemStack}
import net.minecraft.world.item.Item.Properties
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.extensions.common.IClientItemExtensions
import net.minecraftforge.fml.loading.FMLEnvironment

import java.util.function.Consumer

/**
 * A generic armor item class to use custom armor models managed by the
 * [[ArmorModelManager]].
 *
 * @param material the material of the armor
 * @param slot the slot the armor can be worn in
 * @param properties the item properties
 */
class CustomArmorModelItem(material: ArmorMaterial, slot: EquipmentSlot, properties: Properties)
    extends ArmorItem(material, slot, properties) {

    override def initializeClient(consumer: Consumer[IClientItemExtensions]): Unit = consumer.accept(new IClientItemExtensions {
        override def getGenericArmorModel(entity: LivingEntity, stack: ItemStack, slot: EquipmentSlot, original: HumanoidModel[_]): Model =
            // Check sides in case of illegal calling
            if (FMLEnvironment.dist == Dist.CLIENT) MultilingualExamplesClient.armorModelManager.getArmorModel(material, entity, stack, slot, original)
            else super.getGenericArmorModel(entity, stack, slot, original)
    })

    override def getArmorTexture(stack: ItemStack, entity: Entity, slot: EquipmentSlot, `type`: String): String =
        // Check sides in case of illegal calling
        if (FMLEnvironment.dist == Dist.CLIENT) MultilingualExamplesClient.armorModelManager.getTexture(material, stack, entity, slot, Option(`type`))
        else super.getArmorTexture(stack, entity, slot, `type`)
}
