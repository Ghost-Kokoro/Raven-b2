package net.bplaced.azoq.mixin.mixins;

import net.bplaced.azoq.Client;
import net.bplaced.azoq.module.modules.combat.ModuleHitBox;
import net.bplaced.azoq.module.modules.movement.ModuleNoCobweb;
import net.bplaced.azoq.module.modules.movement.ModuleVelocity;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ Entity.class })
public abstract class MixinEntity {
    @Shadow
    public double motionX;
    @Shadow
    public double motionY;
    @Shadow
    public double motionZ;
    @Shadow
    protected boolean isInWeb;
    
    @Inject(method = "getCollisionBorderSize", at = { @At("HEAD") }, cancellable = true)
    public void getCollisionBorderSize(final CallbackInfoReturnable<Float> callbackInfoReturnable) {
        if (Client.INSTANCE.getModuleManager().getModule(ModuleHitBox.class).isEnabled() && ModuleHitBox.getItem()) {
            final double hitBox = Client.INSTANCE.getSettingsManager().getSettingByName("HitBox:HitBox").getValDouble() / 100.0;
            callbackInfoReturnable.setReturnValue((float) hitBox);
            callbackInfoReturnable.cancel();
        }
    }
    
    @Inject(method = "setVelocity", at = { @At("HEAD") }, cancellable = true)
    public void setVelocity(final double x, final double y, final double z, final CallbackInfo callbackInfo) {
        if (Client.INSTANCE.getModuleManager().getModule(ModuleVelocity.class).isEnabled() && Client.INSTANCE.getSettingsManager().getSettingByName("Velocity:Mode").getValString().equalsIgnoreCase("NCP") && ModuleVelocity.velocity) {
            final double velocityXZ = Client.INSTANCE.getSettingsManager().getSettingByName("Velocity:NCP XZ").getValDouble() / 100.0;
            final double velocityY = Client.INSTANCE.getSettingsManager().getSettingByName("Velocity:NCP Y").getValDouble() / 100.0;
            this.motionX = x * velocityXZ;
            this.motionY = y * velocityY;
            this.motionZ = z * velocityXZ;
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = "setInWeb", at = { @At("RETURN") })
    public void setInWeb(final CallbackInfo callbackInfo) {
        try {
            if (Client.INSTANCE.getModuleManager().getModule(ModuleNoCobweb.class).isEnabled()) {
                this.isInWeb = false;
                this.motionX *= 0.10000000149011612;
                this.motionY *= 0.10000000149011612;
                this.motionZ *= 0.10000000149011612;
                callbackInfo.cancel();
            }
        }
        catch (Exception ex) {}
    }
}
