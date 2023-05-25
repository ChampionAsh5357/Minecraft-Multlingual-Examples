/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.mixin

import com.mojang.blaze3d.vertex.PoseStack
import net.ashwork.mc.multilingualexamples.client.MultilingualExamplesClient
import net.minecraft.client.model.HumanoidModel
import net.minecraft.client.renderer.{MultiBufferSource, RenderType}
import net.minecraft.client.renderer.entity.{ItemRenderer, RenderLayerParent}
import net.minecraft.client.renderer.entity.layers.{HumanoidArmorLayer, RenderLayer}
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.{EquipmentSlot, LivingEntity}
import net.minecraft.world.item.ArmorItem
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import org.spongepowered.asm.mixin.injection.{At, Inject}
import org.spongepowered.asm.mixin.{Mixin, Shadow, Unique}

/**
 * A mixin used to inject logic into [[HumanoidArmorLayer]].
 *
 * This is used in place of quilt's mixin as it does not allow general armor
 * models to be rendered onto a humanoid parent model.
 *
 * @param parent the parent renderer
 */
@Mixin(Array(classOf[HumanoidArmorLayer[_, _, _]]))
abstract class HumanoidArmorLayerMixin private (parent: RenderLayerParent[LivingEntity, HumanoidModel[LivingEntity]]) extends RenderLayer[LivingEntity, HumanoidModel[LivingEntity]](parent) {

    @Shadow
    private def getArmorLocation(item: ArmorItem, usesInnerModel: Boolean, textureSuffix: String): ResourceLocation =
        throw AssertionError("Method is shadowed from HumanoidArmorLayer!")

    @Unique
    private var multilingualExamplesCapturedEntity: Option[LivingEntity] = None

    @Unique
    private var multilingualExamplesCapturedSlot: Option[EquipmentSlot] = None

    /**
     * A method used to capture the entity the armor is being rendered for.
     *
     * @param pose        the transformations to apply to the rendering object
     * @param source      the vbo holder
     * @param packedLight the compressed block and skylight values
     * @param entity      the entity the armor is rendered on
     * @param position    the lerped position of the entity
     * @param speed       the lerped speed of the entity
     * @param partialTick the delta when the frame is rendered between ticks
     * @param bob         the amount of bob on the head
     * @param yRot        the y rotation of the entity
     * @param xRot        the x rotation of the entity
     * @param ci          a callback containing information about the injection
     */
    @Inject(at = Array(At(value = "HEAD")), method = Array("render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V"))
    private def multilingualExamplesCaptureEntity(pose: PoseStack, source: MultiBufferSource, packedLight: Int, entity: LivingEntity, position: Float, speed: Float, partialTick: Float, bob: Float, yRot: Float, xRot: Float, ci: CallbackInfo): Unit =
        this.multilingualExamplesCapturedEntity = Some(entity)

    /**
     * A method used to capture the slot holding the armor being rendered.
     *
     * @param pose          the transformations to apply to the rendering object
     * @param source        the vbo holder
     * @param entity        the entity the armor is rendered on
     * @param slot          the slot being rendered for the armor
     * @param packedLight   the compressed block and skylight values
     * @param originalModel the original armor model to be rendered
     * @param ci            a callback containing information about the injection
     */
    @Inject(at = Array(At(value = "HEAD")), method = Array("renderArmorPiece"))
    private def multilingualExamplesCaptureSlot(pose: PoseStack, source: MultiBufferSource, entity: LivingEntity, slot: EquipmentSlot, packedLight: Int, originalModel: HumanoidModel[_], ci: CallbackInfo): Unit =
        this.multilingualExamplesCapturedSlot = Some(slot)

    /**
     * A method to release the captured data at the end of the rendering.
     *
     * @param pose        the transformations to apply to the rendering object
     * @param source      the vbo holder
     * @param packedLight the compressed block and skylight values
     * @param entity      the entity the armor is rendered on
     * @param position    the lerped position of the entity
     * @param speed       the lerped speed of the entity
     * @param partialTick the delta when the frame is rendered between ticks
     * @param bob         the amount of bob on the head
     * @param yRot        the y rotation of the entity
     * @param xRot        the x rotation of the entity
     * @param ci          a callback containing information about the injection
     */
    @Inject(at = Array(At(value = "RETURN")), method = Array("render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V"))
    private def multilingualExamplesRelease(pose: PoseStack, source: MultiBufferSource, packedLight: Int, entity: LivingEntity, position: Float, speed: Float, partialTick: Float, bob: Float, yRot: Float, xRot: Float, ci: CallbackInfo): Unit = {
        this.multilingualExamplesCapturedEntity = None
        this.multilingualExamplesCapturedSlot = None
    }

    @Inject(at = Array(At(value = "HEAD")), method = Array("renderModel"), cancellable = true)
    private def renderArmor(pose: PoseStack, source: MultiBufferSource, packedLight: Int, item: ArmorItem, hasFoil: Boolean, originalModel: HumanoidModel[_], useInnerModel: Boolean, r: Float, g: Float, b: Float, textureSuffix: String, ci: CallbackInfo): Unit = {
        // Check if a handler exists
        if (MultilingualExamplesClient.armorModelManager.hasHandler(item)) {
            val entity = this.multilingualExamplesCapturedEntity.get
            val slot = this.multilingualExamplesCapturedSlot.get
            val handler = MultilingualExamplesClient.armorModelManager.getHandler(item.getMaterial, entity)

            // Get intermediary values
            val stack = entity.getItemBySlot(slot)
            val texture = handler.getArmorTexture(this.getArmorLocation(item, useInnerModel, textureSuffix), entity, stack, slot, useInnerModel, textureSuffix)
            val renderType = handler.getArmorRenderLayer(RenderType.armorCutoutNoCull(texture), entity, stack, slot, texture)

            // Render model and cancel event
            val vbo = ItemRenderer.getArmorFoilBuffer(source, renderType, false, hasFoil)
            handler.getAndSetup(entity, stack, slot, this.getParentModel)
                    .renderToBuffer(pose, vbo, packedLight, OverlayTexture.NO_OVERLAY, r, g, b, 1.0f)
            ci.cancel()
        }
    }
}
