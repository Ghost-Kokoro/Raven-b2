package net.bplaced.azoq.mixin.mixins;

import net.bplaced.azoq.Client;
import net.bplaced.azoq.event.eventapi.EventManager;
import net.bplaced.azoq.event.eventapi.events.Event;
import net.bplaced.azoq.event.events.EventRenderPlayer;
import net.bplaced.azoq.module.modules.render.ModulePlayerEsp;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ RenderPlayer.class })
public class MixinRenderPlayer {
    @Inject(method = "doRender", at = { @At("HEAD") })
    public void doRender(final AbstractClientPlayer entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks, final CallbackInfo callbackInfo) {
        EventManager.call((Event)new EventRenderPlayer((EntityLivingBase)entity, x, y, z));
    }
    
    @Inject(method = "doRender", at = { @At("HEAD") })
    public void doRenderPre(final AbstractClientPlayer entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks, final CallbackInfo callbackInfo) {
        if (Client.INSTANCE.getModuleManager().getModule(ModulePlayerEsp.class).isEnabled()) {
            GL11.glEnable(32823);
            GL11.glPolygonOffset(1.0f, -1100000.0f);
        }
    }
    
    @Inject(method = "doRender", at = { @At("RETURN") })
    public void doRenderPost(final AbstractClientPlayer entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks, final CallbackInfo callbackInfo) {
        if (Client.INSTANCE.getModuleManager().getModule(ModulePlayerEsp.class).isEnabled()) {
            GL11.glPolygonOffset(1.0f, 1100000.0f);
            GL11.glDisable(32823);
        }
    }
}
