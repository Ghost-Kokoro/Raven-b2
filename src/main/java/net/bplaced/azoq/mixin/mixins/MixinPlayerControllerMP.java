package net.bplaced.azoq.mixin.mixins;

import net.bplaced.azoq.Client;
import net.bplaced.azoq.module.modules.combat.ModuleRange;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerControllerMP.class)
public class MixinPlayerControllerMP {
    @Inject(method = "getBlockReachDistance", at = { @At("HEAD") }, cancellable = true)
    public void getBlockReachDistance(final CallbackInfoReturnable<Float> callbackInfoReturnable) {
        if (Client.INSTANCE.getModuleManager().getModule(ModuleRange.class).isEnabled() && ModuleRange.getItem()) {
            callbackInfoReturnable.setReturnValue((float)Client.INSTANCE.getSettingsManager().getSettingByName("Range:Range").getValDouble());
            callbackInfoReturnable.cancel();
        }
    }
    
    @Inject(method = "extendedReach", at = { @At("HEAD") }, cancellable = true)
    public void extendedReach(final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (Client.INSTANCE.getModuleManager().getModule(ModuleRange.class).isEnabled() && ModuleRange.getItem()) {
            callbackInfoReturnable.setReturnValue(true);
            callbackInfoReturnable.cancel();
        }
    }
}
