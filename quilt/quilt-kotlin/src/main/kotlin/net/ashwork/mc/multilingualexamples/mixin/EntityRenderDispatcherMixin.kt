/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.mixin

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
 * A mixin used to inject logic into [EntityRenderDispatcher].
 */
@Mixin(EntityRenderDispatcher::class)
class EntityRenderDispatcherMixin {

    /**
     * A method to inject logic at the last return statement of
     * [EntityRenderDispatcher.onResourceManagerReload].
     *
     * @param manager the object instance
     * @param ci a callback containing information about the injection
     * @param context the provider context from a local variable
     */
    @Inject(at = [At("TAIL")], method = ["onResourceManagerReload"], locals = LocalCapture.CAPTURE_FAILHARD)
    private fun resourceReload(manager: ResourceManager, ci: CallbackInfo, context: EntityRendererProvider.Context) {
        MultilingualExamplesClient.armorModelManager().reloadModels(context.modelSet)
    }
}
