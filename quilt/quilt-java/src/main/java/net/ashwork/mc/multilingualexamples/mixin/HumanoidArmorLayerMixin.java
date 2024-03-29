/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.ashwork.mc.multilingualexamples.client.MultilingualExamplesClient;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * A mixin used to inject logic into {@link HumanoidArmorLayer}.
 *
 * <p>This is used in place of quilt's mixin as it does not allow general armor
 * models to be rendered onto a humanoid parent model.
 */
@Mixin(HumanoidArmorLayer.class)
public abstract class HumanoidArmorLayerMixin extends RenderLayer<LivingEntity, HumanoidModel<LivingEntity>> {

    /**
     * A private constructor, should never be called.
     *
     * @param parent the parent renderer
     */
    private HumanoidArmorLayerMixin(RenderLayerParent<LivingEntity, HumanoidModel<LivingEntity>> parent) {
        super(parent);
    }

    @Shadow
    private ResourceLocation getArmorLocation(ArmorItem item, boolean usesInnerModel, @Nullable String textureSuffix) {
        throw new AssertionError("Method is shadowed from HumanoidArmorLayer!");
    }

    @Unique
    private LivingEntity multilingualExamples$capturedEntity;
    @Unique
    private EquipmentSlot multilingualExamples$capturedSlot;

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
    @Inject(at = @At("HEAD"), method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V")
    private void multilingualExamples$captureEntity(PoseStack pose, MultiBufferSource source, int packedLight, LivingEntity entity, float position, float speed, float partialTick, float bob, float yRot, float xRot, CallbackInfo ci) {
        this.multilingualExamples$capturedEntity = entity;
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
    @Inject(at = @At("HEAD"), method = "renderArmorPiece")
    private void multilingualExamples$captureSlot(PoseStack pose, MultiBufferSource source, LivingEntity entity, EquipmentSlot slot, int packedLight, HumanoidModel<?> originalModel, CallbackInfo ci) {
        this.multilingualExamples$capturedSlot = slot;
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
    @Inject(at = @At("RETURN"), method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V")
    private void multilingualExamples$release(PoseStack pose, MultiBufferSource source, int packedLight, LivingEntity entity, float position, float speed, float partialTick, float bob, float yRot, float xRot, CallbackInfo ci) {
        this.multilingualExamples$capturedEntity = null;
        this.multilingualExamples$capturedSlot = null;
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
    @Inject(at = @At("HEAD"), method = "renderModel", cancellable = true)
    private void renderArmor(PoseStack pose, MultiBufferSource source, int packedLight, ArmorItem item, boolean hasFoil, HumanoidModel<?> originalModel, boolean useInnerModel, float r, float g, float b, @Nullable String textureSuffix, CallbackInfo ci) {
        // Check if a handler exists
        if (MultilingualExamplesClient.instance().armorModelManager().hasHandler(item)) {
            var handler = MultilingualExamplesClient.instance().armorModelManager().getHandler(item.getMaterial(), this.multilingualExamples$capturedEntity);

            // Get intermediary values
            var stack = this.multilingualExamples$capturedEntity.getItemBySlot(this.multilingualExamples$capturedSlot);
            var texture = handler.getArmorTexture(this.getArmorLocation(item, useInnerModel, textureSuffix), this.multilingualExamples$capturedEntity, stack, this.multilingualExamples$capturedSlot, useInnerModel, textureSuffix);
            var renderType = handler.getArmorRenderLayer(RenderType.armorCutoutNoCull(texture), this.multilingualExamples$capturedEntity, stack, this.multilingualExamples$capturedSlot, texture);

            // Render model and cancel event
            var vbo = ItemRenderer.getArmorFoilBuffer(source, renderType, false, hasFoil);
            handler.getAndSetup(this.multilingualExamples$capturedEntity, stack, this.multilingualExamples$capturedSlot, this.getParentModel())
                    .renderToBuffer(pose, vbo, packedLight, OverlayTexture.NO_OVERLAY, r, g, b, 1.0f);
            ci.cancel();
        }
    }
}
