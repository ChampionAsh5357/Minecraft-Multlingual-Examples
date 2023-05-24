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
     */
    @Inject(at = @At("HEAD"), method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V")
    private void multilingualExamples$captureEntity(PoseStack pose, MultiBufferSource source, int combinedLight, LivingEntity entity, float position, float speed, float partialTick, float bob, float yRot, float xRot, CallbackInfo ci) {
        this.multilingualExamples$capturedEntity = entity;
    }

    /**
     * A method used to capture the slot holding the armor being rendered.
     */
    @Inject(at = @At("HEAD"), method = "renderArmorPiece")
    private void multilingualExamples$captureSlot(PoseStack pose, MultiBufferSource source, LivingEntity entity, EquipmentSlot slot, int combinedLight, HumanoidModel<?> originalModel, CallbackInfo ci) {
        this.multilingualExamples$capturedSlot = slot;
    }

    /**
     * A method to release the captured data at the end of the rendering.
     */
    @Inject(at = @At("RETURN"), method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V")
    private void multilingualExamples$release(PoseStack pose, MultiBufferSource source, int combinedLight, LivingEntity entity, float position, float speed, float partialTick, float bob, float yRot, float xRot, CallbackInfo ci) {
        this.multilingualExamples$capturedEntity = null;
        this.multilingualExamples$capturedSlot = null;
    }

    /**
     * A method to inject logic when attempting to render a model.
     */
    @Inject(at = @At("HEAD"), method = "renderModel", cancellable = true)
    private void renderArmor(PoseStack pose, MultiBufferSource source, int combinedLight, ArmorItem item, boolean hasFoil, HumanoidModel<?> originalModel, boolean useInnerModel, float r, float g, float b, @Nullable String textureSuffix, CallbackInfo ci) {
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
                    .renderToBuffer(pose, vbo, combinedLight, OverlayTexture.NO_OVERLAY, r, g, b, 1.0f);
            ci.cancel();
        }
    }
}
