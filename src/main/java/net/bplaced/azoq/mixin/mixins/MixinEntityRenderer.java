package net.bplaced.azoq.mixin.mixins;

import net.bplaced.azoq.Client;
import net.bplaced.azoq.event.eventapi.EventManager;
import net.bplaced.azoq.event.eventapi.events.Event;
import net.bplaced.azoq.event.events.EventRenderGameOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ EntityRenderer.class })
public class MixinEntityRenderer {
    @Inject(method = "updateCameraAndRender", at = { @At("RETURN") })
    public void updateCameraAndRender(final float p_181560_1_, final long p_181560_2_, final CallbackInfo callbackInfo) {
        if (!Minecraft.getMinecraft().skipRenderWorld && Minecraft.getMinecraft().theWorld != null && (!Minecraft.getMinecraft().gameSettings.hideGUI || Minecraft.getMinecraft().currentScreen != null) && Client.INSTANCE.isLabyModActive()) {
            GlStateManager.alphaFunc(516, 0.1f);
            EventManager.call((Event)new EventRenderGameOverlay());
        }
    }
}
