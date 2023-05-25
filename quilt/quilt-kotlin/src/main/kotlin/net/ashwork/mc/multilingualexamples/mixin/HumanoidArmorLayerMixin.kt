/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.mixin

import com.mojang.blaze3d.vertex.PoseStack
import net.ashwork.mc.multilingualexamples.client.MultilingualExamplesClient
import net.minecraft.client.model.HumanoidModel
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.ItemRenderer
import net.minecraft.client.renderer.entity.RenderLayerParent
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer
import net.minecraft.client.renderer.entity.layers.RenderLayer
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ArmorItem
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.Unique
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo


/**
 * A mixin used to inject logic into [HumanoidArmorLayer].
 *
 * This is used in place of quilt's mixin as it does not allow general armor
 * models to be rendered onto a humanoid parent model.
 *
 * @param parent the parent renderer
 */
@Mixin(HumanoidArmorLayer::class)
abstract class HumanoidArmorLayerMixin private constructor(parent: RenderLayerParent<LivingEntity, HumanoidModel<LivingEntity>>) : RenderLayer<LivingEntity, HumanoidModel<LivingEntity>>(parent) {

    @Shadow
    private fun getArmorLocation(item: ArmorItem, usesInnerModel: Boolean, textureSuffix: String?): ResourceLocation =
        throw AssertionError("Method is shadowed from HumanoidArmorLayer!")

    @Unique
    private var multilingualExamplesCapturedEntity: LivingEntity? = null

    @Unique
    private var multilingualExamplesCapturedSlot: EquipmentSlot? = null

    /**
     * A method used to capture the entity the armor is being rendered for.
     *
     * @param pose the transformations to apply to the rendering object
     * @param source the vbo holder
     * @param packedLight the compressed block and skylight values
     * @param entity the entity the armor is rendered on
     * @param position the lerped position of the entity
     * @param speed the lerped speed of the entity
     * @param partialTick the delta when the frame is rendered between ticks
     * @param bob the amount of bob on the head
     * @param yRot the y rotation of the entity
     * @param xRot the x rotation of the entity
     * @param ci a callback containing information about the injection
     */
    @Inject(at = [At("HEAD")], method = ["render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V"])
    private fun multilingualExamplesCaptureEntity(pose: PoseStack, source: MultiBufferSource, packedLight: Int, entity: LivingEntity, position: Float, speed: Float, partialTick: Float, bob: Float, yRot: Float, xRot: Float, ci: CallbackInfo) {
        this.multilingualExamplesCapturedEntity = entity
    }

    /**
     * A method used to capture the slot holding the armor being rendered.
     *
     * @param pose the transformations to apply to the rendering object
     * @param source the vbo holder
     * @param entity the entity the armor is rendered on
     * @param slot the slot being rendered for the armor
     * @param packedLight the compressed block and skylight values
     * @param originalModel the original armor model to be rendered
     * @param ci a callback containing information about the injection
     */
    @Inject(at = [At("HEAD")], method = ["renderArmorPiece"])
    private fun multilingualExamplesCaptureSlot(pose: PoseStack, source: MultiBufferSource, entity: LivingEntity, slot: EquipmentSlot, packedLight: Int, originalModel: HumanoidModel<*>, ci: CallbackInfo) {
        this.multilingualExamplesCapturedSlot = slot
    }

    /**
     * A method to release the captured data at the end of the rendering.
     *
     * @param pose the transformations to apply to the rendering object
     * @param source the vbo holder
     * @param packedLight the compressed block and skylight values
     * @param entity the entity the armor is rendered on
     * @param position the lerped position of the entity
     * @param speed the lerped speed of the entity
     * @param partialTick the delta when the frame is rendered between ticks
     * @param bob the amount of bob on the head
     * @param yRot the y rotation of the entity
     * @param xRot the x rotation of the entity
     * @param ci a callback containing information about the injection
     */
    @Inject(at = [At("RETURN")], method = ["render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V"])
    private fun multilingualExamplesRelease(pose: PoseStack, source: MultiBufferSource, packedLight: Int, entity: LivingEntity, position: Float, speed: Float, partialTick: Float, bob: Float, yRot: Float, xRot: Float, ci: CallbackInfo) {
        this.multilingualExamplesCapturedEntity = null
        this.multilingualExamplesCapturedSlot = null
    }

    /**
     * A method to inject logic when attempting to render a model.
     *
     * @param pose the transformations to apply to the rendering object
     * @param source the vbo holder
     * @param packedLight the compressed block and skylight values
     * @param item the armor item being rendered on the player
     * @param hasFoil {@code true} when the armor has a foil-like effect, usually when enchanted
     * @param originalModel the original armor model to be rendered
     * @param useInnerModel {@code true} when the inner model, usually for leggings, should be used
     * @param r a [0,1] value containing the red tint to apply
     * @param g a [0,1] value containing the green tint to apply
     * @param b a [0,1] value containing the blue tint to apply
     * @param textureSuffix the suffix of the texture to apply to the armor model
     * @param ci a callback containing information about the injection
     */
    @Inject(at = [At("HEAD")], method = ["renderModel"], cancellable = true)
    private fun renderArmor(pose: PoseStack, source: MultiBufferSource, packedLight: Int, item: ArmorItem, hasFoil: Boolean, originalModel: HumanoidModel<*>, useInnerModel: Boolean, r: Float, g: Float, b: Float, textureSuffix: String?, ci: CallbackInfo) {
        // Check if a handler exists
        if (MultilingualExamplesClient.armorModelManager().hasHandler(item)) {
            val entity = this.multilingualExamplesCapturedEntity!!
            val slot = this.multilingualExamplesCapturedSlot!!
            val handler = MultilingualExamplesClient.armorModelManager().getHandler(item.material, entity)

            // Get intermediary values
            val stack = entity.getItemBySlot(slot)
            val texture = handler.getArmorTexture(this.getArmorLocation(item, useInnerModel, textureSuffix), entity, stack, slot, useInnerModel, textureSuffix)
            val renderType = handler.getArmorRenderLayer(RenderType.armorCutoutNoCull(texture), entity, stack, slot, texture)

            // Render model and cancel event
            val vbo = ItemRenderer.getArmorFoilBuffer(source, renderType, false, hasFoil)
            handler.getAndSetup(entity, stack, slot, this.parentModel)
                .renderToBuffer(pose, vbo, packedLight, OverlayTexture.NO_OVERLAY, r, g, b, 1.0f)
            ci.cancel()
        }
    }
}
