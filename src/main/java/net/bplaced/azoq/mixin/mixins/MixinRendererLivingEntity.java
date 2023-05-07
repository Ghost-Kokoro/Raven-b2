package net.bplaced.azoq.mixin.mixins;

import net.bplaced.azoq.Client;
import net.bplaced.azoq.module.modules.render.ModuleNameTags;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RendererLivingEntity.class)
public class MixinRendererLivingEntity {
    @Inject(method = "canRenderName", at = { @At("HEAD") }, cancellable = true)
    protected void canRenderName(final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (Client.INSTANCE.getModuleManager().getModule(ModuleNameTags.class).isEnabled()) {
            callbackInfoReturnable.setReturnValue(false);
            callbackInfoReturnable.cancel();
        }
    }
}
