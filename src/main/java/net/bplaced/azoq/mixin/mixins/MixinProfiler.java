package net.bplaced.azoq.mixin.mixins;

import net.bplaced.azoq.event.eventapi.EventManager;
import net.bplaced.azoq.event.eventapi.events.Event;
import net.bplaced.azoq.event.events.EventRenderWorld;
import net.minecraft.profiler.Profiler;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ Profiler.class })
public class MixinProfiler {
    @Inject(method = "endStartSection", at = { @At("HEAD") })
    public void endStartSection(final String name, final CallbackInfo callbackInfo) {
        if (name.equals("weather")) {
            EventManager.call((Event)new EventRenderWorld());
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
}
