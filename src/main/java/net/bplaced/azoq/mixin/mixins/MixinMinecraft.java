package net.bplaced.azoq.mixin.mixins;

import java.util.Iterator;
import net.bplaced.azoq.Client;
import net.bplaced.azoq.event.eventapi.EventManager;
import net.bplaced.azoq.event.eventapi.events.Event;
import net.bplaced.azoq.event.events.EventMouse;
import net.bplaced.azoq.event.events.EventRunTick;
import net.bplaced.azoq.module.Module;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {
	
    @Inject(method = "startGame", at = { @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;ingameGUI:Lnet/minecraft/client/gui/GuiIngame;", shift = At.Shift.AFTER) })
    private void startGame(final CallbackInfo callbackInfo) {
        Client.INSTANCE.startGame();
    }
    
    @Inject(method = "runTick", at = { @At("HEAD") })
    public void runTick(final CallbackInfo callbackInfo) {
        EventManager.call((Event)new EventRunTick());
    }
    
    @Inject(method = "runTick", at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;dispatchKeypresses()V", shift = At.Shift.AFTER) })
    public void keyPresses(final CallbackInfo callbackInfo) {
        if (Keyboard.getEventKeyState() && Minecraft.getMinecraft().currentScreen == null) {
            for (final Module m : Client.INSTANCE.getModuleManager().getModules()) {
                if (Keyboard.getEventKey() == m.getKeyBind()) {
                    m.toggleModule();
                }
            }
        }
    }
    
    @Inject(method = "clickMouse", at = { @At("HEAD") })
    private void clickMouse(final CallbackInfo callbackInfo) {
        EventManager.call((Event)new EventMouse(EventMouse.Button.Left));
    }
    
    @Inject(method = "rightClickMouse", at = { @At("HEAD") })
    private void rightClickMouse(final CallbackInfo callbackInfo) {
        EventManager.call((Event)new EventMouse(EventMouse.Button.Right));
    }
    
    @Inject(method = "middleClickMouse", at = { @At("HEAD") })
    private void middleClickMouse(final CallbackInfo callbackInfo) {
        EventManager.call((Event)new EventMouse(EventMouse.Button.Middle));
    }
}
