/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
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

@Mixin(EntityRenderDispatcher::class)
class EntityRenderDispatcherMixin {

    @Inject(at = [At("TAIL")], method = ["onResourceManagerReload"], locals = LocalCapture.CAPTURE_FAILHARD)
    private fun resourceReload(manager: ResourceManager, ci: CallbackInfo, context: EntityRendererProvider.Context) {
        MultilingualExamplesClient.armorModelManager().reloadModels(context.modelSet)
    }
}
