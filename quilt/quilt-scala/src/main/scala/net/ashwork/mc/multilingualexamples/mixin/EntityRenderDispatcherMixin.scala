/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.mixin

import net.ashwork.mc.multilingualexamples.client.MultilingualExamplesClient
import net.minecraft.client.renderer.entity.{EntityRenderDispatcher, EntityRendererProvider}
import net.minecraft.server.packs.resources.ResourceManager
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.callback.{CallbackInfo, LocalCapture}
import org.spongepowered.asm.mixin.injection.{At, Inject}

/**
 * A mixin used to inject logic into [[EntityRenderDispatcher]].
 */
@Mixin(Array(classOf[EntityRenderDispatcher]))
class EntityRenderDispatcherMixin {

    /**
     * A method to inject logic at the last return statement of
     * [[EntityRenderDispatcher.onResourceManagerReload]].
     *
     * @param manager the object instance
     * @param ci a callback containing information about the injection
     * @param context the provider context from a local variable
     */
    @Inject(at = Array(At(value = "TAIL")), method = Array("onResourceManagerReload"), locals = LocalCapture.CAPTURE_FAILHARD)
    private def resourceReload(manager: ResourceManager, ci: CallbackInfo, context: EntityRendererProvider.Context): Unit =
        MultilingualExamplesClient.armorModelManager.reloadModels(context.getModelSet)
}
