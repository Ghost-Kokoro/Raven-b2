package net.bplaced.azoq.mixin.mixins;

import net.bplaced.azoq.Client;
import net.bplaced.azoq.module.modules.movement.ModuleKeepSprint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.potion.Potion;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.ForgeHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EntityPlayer.class)
public class MixinEntityPlayer {
	
    @Overwrite
    public void attackTargetEntityWithCurrentItem(final Entity targetEntity) {
        if (!ForgeHooks.onPlayerAttackTarget((EntityPlayer)Minecraft.getMinecraft().thePlayer, targetEntity)) {
            return;
        }
        if (targetEntity.canAttackWithItem() && !targetEntity.hitByEntity((Entity)Minecraft.getMinecraft().thePlayer)) {
            float f = (float)Minecraft.getMinecraft().thePlayer.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
            int i = 0;
            float f2 = 0.0f;
            if (targetEntity instanceof EntityLivingBase) {
                f2 = EnchantmentHelper.getModifierForCreature(Minecraft.getMinecraft().thePlayer.getHeldItem(), ((EntityLivingBase)targetEntity).getCreatureAttribute());
            }
            else {
                f2 = EnchantmentHelper.getModifierForCreature(Minecraft.getMinecraft().thePlayer.getHeldItem(), EnumCreatureAttribute.UNDEFINED);
            }
            i += EnchantmentHelper.getKnockbackModifier((EntityLivingBase)Minecraft.getMinecraft().thePlayer);
            if (Minecraft.getMinecraft().thePlayer.isSprinting()) {
                ++i;
            }
            if (f > 0.0f || f2 > 0.0f) {
                final boolean flag = Minecraft.getMinecraft().thePlayer.fallDistance > 0.0f && !Minecraft.getMinecraft().thePlayer.onGround && !Minecraft.getMinecraft().thePlayer.isOnLadder() && !Minecraft.getMinecraft().thePlayer.isInWater() && !Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.blindness) && Minecraft.getMinecraft().thePlayer.ridingEntity == null && targetEntity instanceof EntityLivingBase;
                if (flag && f > 0.0f) {
                    f *= 1.5f;
                }
                f += f2;
                boolean flag2 = false;
                final int j = EnchantmentHelper.getFireAspectModifier((EntityLivingBase)Minecraft.getMinecraft().thePlayer);
                if (targetEntity instanceof EntityLivingBase && j > 0 && !targetEntity.isBurning()) {
                    flag2 = true;
                    targetEntity.setFire(1);
                }
                final double d0 = targetEntity.motionX;
                final double d2 = targetEntity.motionY;
                final double d3 = targetEntity.motionZ;
                final boolean flag3 = targetEntity.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)Minecraft.getMinecraft().thePlayer), f);
                if (flag3) {
                    if (i > 0) {
                        targetEntity.addVelocity((double)(-MathHelper.sin(Minecraft.getMinecraft().thePlayer.rotationYaw * 3.1415927f / 180.0f) * i * 0.5f), 0.1, (double)(MathHelper.cos(Minecraft.getMinecraft().thePlayer.rotationYaw * 3.1415927f / 180.0f) * i * 0.5f));
                        if (!Client.INSTANCE.getModuleManager().getModule(ModuleKeepSprint.class).isEnabled()) {
                            final EntityPlayerSP thePlayer = Minecraft.getMinecraft().thePlayer;
                            thePlayer.motionX *= 0.6;
                            final EntityPlayerSP thePlayer2 = Minecraft.getMinecraft().thePlayer;
                            thePlayer2.motionZ *= 0.6;
                            Minecraft.getMinecraft().thePlayer.setSprinting(false);
                        }
                        if (Client.INSTANCE.getSettingsManager().getSettingByName("KeepSprint:FakeFOV").getValBoolean()) {
                            ModuleKeepSprint.fakeFOV = true;
                        }
                    }
                    if (targetEntity instanceof EntityPlayerMP && targetEntity.velocityChanged) {
                        ((EntityPlayerMP)targetEntity).playerNetServerHandler.sendPacket((Packet)new S12PacketEntityVelocity(targetEntity));
                        targetEntity.velocityChanged = false;
                        targetEntity.motionX = d0;
                        targetEntity.motionY = d2;
                        targetEntity.motionZ = d3;
                    }
                    if (flag) {
                        Minecraft.getMinecraft().thePlayer.onCriticalHit(targetEntity);
                    }
                    if (f2 > 0.0f) {
                        Minecraft.getMinecraft().thePlayer.onEnchantmentCritical(targetEntity);
                    }
                    if (f >= 18.0f) {
                        Minecraft.getMinecraft().thePlayer.triggerAchievement((StatBase)AchievementList.overkill);
                    }
                    Minecraft.getMinecraft().thePlayer.setLastAttacker(targetEntity);
                    if (targetEntity instanceof EntityLivingBase) {
                        EnchantmentHelper.applyThornEnchantments((EntityLivingBase)targetEntity, (Entity)Minecraft.getMinecraft().thePlayer);
                    }
                    EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase)Minecraft.getMinecraft().thePlayer, targetEntity);
                    final ItemStack itemstack = Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem();
                    Entity entity = targetEntity;
                    if (targetEntity instanceof EntityDragonPart) {
                        final IEntityMultiPart ientitymultipart = ((EntityDragonPart)targetEntity).entityDragonObj;
                        if (ientitymultipart instanceof EntityLivingBase) {
                            entity = (Entity)ientitymultipart;
                        }
                    }
                    if (itemstack != null && entity instanceof EntityLivingBase) {
                        itemstack.hitEntity((EntityLivingBase)entity, (EntityPlayer)Minecraft.getMinecraft().thePlayer);
                        if (itemstack.stackSize <= 0) {
                            Minecraft.getMinecraft().thePlayer.destroyCurrentEquippedItem();
                        }
                    }
                    if (targetEntity instanceof EntityLivingBase) {
                        Minecraft.getMinecraft().thePlayer.addStat(StatList.damageDealtStat, Math.round(f * 10.0f));
                        if (j > 0) {
                            targetEntity.setFire(j * 4);
                        }
                    }
                    Minecraft.getMinecraft().thePlayer.addExhaustion(0.3f);
                }
                else if (flag2) {
                    targetEntity.extinguish();
                }
            }
        }
    }
}
