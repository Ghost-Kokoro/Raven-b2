package net.bplaced.azoq.mixin.mixins;

import java.util.Iterator;
import net.bplaced.azoq.Client;
import net.bplaced.azoq.config.configs.ModuleRemoveConfig;
import net.bplaced.azoq.config.configs.ModuleSaveConfig;
import net.bplaced.azoq.event.eventapi.EventManager;
import net.bplaced.azoq.event.eventapi.events.Event;
import net.bplaced.azoq.event.events.EventUpdate;
import net.bplaced.azoq.event.events.EventUpdateWalkingPlayer;
import net.bplaced.azoq.module.Module;
import net.bplaced.azoq.module.modules.settings.ModulePanic;
import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ EntityPlayerSP.class })
public class MixinEntityPlayerSP {
    @Inject(method = "onUpdate", at = { @At("HEAD") })
    public void onUpdate(final CallbackInfo callbackInfo) {
        EventManager.call((Event)new EventUpdate());
    }
    
    @Inject(method = "onUpdateWalkingPlayer", at = { @At("HEAD") })
    public void onUpdateWalkingPlayer(final CallbackInfo callbackInfo) {
        EventManager.call((Event)new EventUpdateWalkingPlayer());
    }
    
    @Inject(method = "sendChatMessage", at = { @At("HEAD") }, cancellable = true)
    public void sendChatMessage(final String message, final CallbackInfo callbackInfo) {
        final Module panic = Client.INSTANCE.getModuleManager().getModule(ModulePanic.class);
        if (panic.isEnabled()) {
            if (message.equalsIgnoreCase("J")) {
                for (final Module m : Client.INSTANCE.getModuleManager().getModules()) {
                    if (m != panic) {
                        m.setEnabled(false);
                        m.setKeyBind(-1);
                    }
                    else {
                        if (m != panic) {
                            continue;
                        }
                        m.setKeyBind(-1);
                    }
                }
                callbackInfo.cancel();
            }
            else {
                panic.toggleModule();
                callbackInfo.cancel();
            }
        }
        final Module removeConfig = Client.INSTANCE.getModuleManager().getModule(ModuleRemoveConfig.class);
        if (removeConfig.isEnabled()) {
            if (message == null || message.equalsIgnoreCase("")) {
                removeConfig.toggleModule();
                callbackInfo.cancel();
            }
            Client.INSTANCE.getConfigManager().removeConfig(message);
            removeConfig.toggleModule();
            callbackInfo.cancel();
        }
        final Module saveConfig = Client.INSTANCE.getModuleManager().getModule(ModuleSaveConfig.class);
        if (saveConfig.isEnabled()) {
            if (message == null || message.equalsIgnoreCase("")) {
                saveConfig.toggleModule();
                callbackInfo.cancel();
            }
            Client.INSTANCE.getConfigManager().createConfig(message);
            saveConfig.toggleModule();
            callbackInfo.cancel();
        }
    }
}
