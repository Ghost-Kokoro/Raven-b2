package net.bplaced.azoq.mixin.mixins;

import net.bplaced.azoq.Client;
import net.bplaced.azoq.event.eventapi.EventManager;
import net.bplaced.azoq.event.eventapi.events.Event;
import net.bplaced.azoq.event.events.EventRenderGameOverlay;
import net.bplaced.azoq.module.modules.render.ModuleNoScoreBoard;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.scoreboard.ScoreObjective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ GuiIngame.class })
public class MixinGuiIngame {
    @Inject(method = "renderScoreboard", at = { @At("HEAD") }, cancellable = true)
    private void renderScoreboard(final ScoreObjective p_180475_1_, final ScaledResolution p_180475_2_, final CallbackInfo callbackInfo) {
        if (Client.INSTANCE.getModuleManager().getModule(ModuleNoScoreBoard.class).isEnabled()) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = "renderTooltip", at = { @At("RETURN") })
    protected void renderTooltip(final ScaledResolution sr, final float partialTicks, final CallbackInfo callbackInfo) {
        EventManager.call((Event)new EventRenderGameOverlay());
    }
}
