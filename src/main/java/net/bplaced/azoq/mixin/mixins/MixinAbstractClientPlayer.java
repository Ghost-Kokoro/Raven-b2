package net.bplaced.azoq.mixin.mixins;

import net.bplaced.azoq.module.modules.movement.ModuleKeepSprint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraftforge.client.ForgeHooksClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin({ AbstractClientPlayer.class })
public class MixinAbstractClientPlayer {
    @Overwrite
    public float getFovModifier() {
        float f = 1.0f;
        if (Minecraft.getMinecraft().thePlayer.capabilities.isFlying) {
            f *= 1.1f;
        }
        final IAttributeInstance iattributeinstance = Minecraft.getMinecraft().thePlayer.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
        f *= (float)((iattributeinstance.getAttributeValue() / Minecraft.getMinecraft().thePlayer.capabilities.getWalkSpeed() + 1.0) / 2.0);
        if (Minecraft.getMinecraft().thePlayer.capabilities.getWalkSpeed() == 0.0f || Float.isNaN(f) || Float.isInfinite(f)) {
            f = 1.0f;
        }
        if (ModuleKeepSprint.fakeFOV && Minecraft.getMinecraft().thePlayer.isSprinting()) {
            f *= 0.9f;
            ModuleKeepSprint.fakeFOV = false;
        }
        if (Minecraft.getMinecraft().thePlayer.isUsingItem() && Minecraft.getMinecraft().thePlayer.getItemInUse().getItem() == Items.bow) {
            final int i = Minecraft.getMinecraft().thePlayer.getItemInUseDuration();
            float f2 = i / 20.0f;
            if (f2 > 1.0f) {
                f2 = 1.0f;
            }
            else {
                f2 *= f2;
            }
            f *= 1.0f - f2 * 0.15f;
        }
        return ForgeHooksClient.getOffsetFOV((EntityPlayer)Minecraft.getMinecraft().thePlayer, f);
    }
}
