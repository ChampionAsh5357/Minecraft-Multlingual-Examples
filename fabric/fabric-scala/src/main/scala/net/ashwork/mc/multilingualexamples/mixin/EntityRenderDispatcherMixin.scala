package net.ashwork.mc.multilingualexamples.mixin

import net.ashwork.mc.multilingualexamples.client.MultilingualExamplesClient
import net.minecraft.client.renderer.entity.{EntityRenderDispatcher, EntityRendererProvider}
import net.minecraft.server.packs.resources.ResourceManager
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.{At, Inject}
import org.spongepowered.asm.mixin.injection.callback.{CallbackInfo, LocalCapture}

@Mixin(Array(classOf[EntityRenderDispatcher]))
class EntityRenderDispatcherMixin {

    @Inject(at = Array(At(value = "TAIL")), method = Array("onResourceManagerReload"), locals = LocalCapture.CAPTURE_FAILHARD)
    private def resourceReload(manager: ResourceManager, ci: CallbackInfo, context: EntityRendererProvider.Context): Unit =
        MultilingualExamplesClient.armorModelManager.reloadModels(context.getModelSet)
}
