/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.mixin

import groovy.transform.CompileStatic
import net.ashwork.mc.multilingualexamples.client.MultilingualExamplesClient
import net.minecraft.client.renderer.entity.EntityRenderDispatcher
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.server.packs.resources.ResourceManager
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import org.spongepowered.asm.mixin.injection.callback.LocalCapture

/**
 * A mixin used to inject logic into {@link EntityRenderDispatcher}.
 */
@CompileStatic
@Mixin(EntityRenderDispatcher.class)
class EntityRenderDispatcherMixin {

    /**
     * A method to inject logic at the last return statement of
     * {@link EntityRenderDispatcher#onResourceManagerReload(ResourceManager)}.
     *
     * @param manager the object instance
     * @param ci a callback containing information about the injection
     * @param context the provider context from a local variable
     */
    @Inject(at = @At("TAIL"), method = "onResourceManagerReload", locals = LocalCapture.CAPTURE_FAILHARD)
    private void resourceReload(ResourceManager manager, CallbackInfo ci, EntityRendererProvider.Context context) {
        MultilingualExamplesClient.instance().armorModelManager().reloadModels(context.modelSet)
    }
}
