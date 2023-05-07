package net.bplaced.azoq.module.modules.combat;

import java.util.Iterator;
import net.bplaced.azoq.Client;
import net.bplaced.azoq.event.eventapi.EventTarget;
import net.bplaced.azoq.event.events.EventRunTick;
import net.bplaced.azoq.event.events.EventUpdateWalkingPlayer;
import net.bplaced.azoq.gui.click.settings.Setting;
import net.bplaced.azoq.module.Module;
import net.bplaced.azoq.module.modules.combat.ModuleAntiBots;
import net.bplaced.azoq.module.modules.combat.ModuleTeams;
import net.bplaced.azoq.utils.MathUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;

@Module.Information(moduleName = "SmoothAimbot", displayName = "SmoothAimbot", category = Module.Category.COMBAT)
public class ModuleSmoothAimbot extends Module {
	
	@Override
    public void setup() {
        super.setup();
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("SmoothAimbot:Mode", (Module)this, "All Items", new String[] { "All Items", "Hand/Stick/Sword", "Stick/Sword" }));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("SmoothAimbot:Range", (Module)this, 4.5, 0.0, 10.0, false));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("SmoothAimbot:Horizontal", (Module)this, 0.5, 0.1, 10.0, false));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("SmoothAimbot:Vertical", (Module)this, 0.5, 0.1, 10.0, false));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("SmoothAimbot:FOV", (Module)this, 45.0, 1.0, 360.0, true));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("SmoothAimbot:Only Clicking", (Module)this, false));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("SmoothAimbot:Clicking Speed", (Module)this, 5.0, 1.0, 10.0, true));
    }
    
    @EventTarget
    public void onRunTick(final EventRunTick event) {
        this.setDisplayName(this.getModuleName() + " \2477[" + MathUtils.round(Client.INSTANCE.getSettingsManager().getSettingByName("SmoothAimbot:Range").getValDouble()) + " | H:" + MathUtils.round(Client.INSTANCE.getSettingsManager().getSettingByName("SmoothAimbot:Horizontal").getValDouble()) + " | V:" + MathUtils.round(Client.INSTANCE.getSettingsManager().getSettingByName("SmoothAimbot:Vertical").getValDouble()) + " | FOV:" + (int)Client.INSTANCE.getSettingsManager().getSettingByName("SmoothAimbot:FOV").getValDouble() + "]\247r");
    }
    
    @EventTarget
    public void onUpdateWalkingPlayer(final EventUpdateWalkingPlayer event) {
        if (ModuleSmoothAimbot.mc.objectMouseOver.typeOfHit == MovingObjectType.ENTITY) {
            return;
        }
        for (final Object o : ModuleSmoothAimbot.mc.theWorld.loadedEntityList) {
            if (o instanceof EntityLivingBase && o != ModuleSmoothAimbot.mc.thePlayer) {
                EntityLivingBase e = (EntityLivingBase)o;
                if (ModuleAntiBots.isBot((Entity)e) || ModuleTeams.isTeam((Entity)e) || Client.INSTANCE.getFriendManager().isFriend(e.getName()) || ModuleSmoothAimbot.mc.currentScreen != null || !getItem() || ModuleSmoothAimbot.mc.thePlayer.getDistanceToEntity((Entity)e) > Client.INSTANCE.getSettingsManager().getSettingByName("SmoothAimbot:Range").getValDouble() || !ModuleSmoothAimbot.mc.thePlayer.canEntityBeSeen((Entity)e) || this.getDistFromMouseSq(e) > Client.INSTANCE.getSettingsManager().getSettingByName("SmoothAimbot:FOV").getValDouble() * Client.INSTANCE.getSettingsManager().getSettingByName("SmoothAimbot:FOV").getValDouble()) {
                    continue;
                }
                if (e != null && !Client.INSTANCE.getSettingsManager().getSettingByName("SmoothAimbot:Only Clicking").getValBoolean()) {
                    e = (EntityLivingBase)this.rangeSwitch();
                    this.faceEntity((Entity)e, (float)Client.INSTANCE.getSettingsManager().getSettingByName("SmoothAimbot:Horizontal").getValDouble(), (float)Client.INSTANCE.getSettingsManager().getSettingByName("SmoothAimbot:Vertical").getValDouble());
                }
                else {
                    if (!ModuleSmoothAimbot.mc.gameSettings.keyBindAttack.isKeyDown()) {
                        continue;
                    }
                    for (int i = 0; i < (int)Client.INSTANCE.getSettingsManager().getSettingByName("SmoothAimbot:Clicking Speed").getValDouble(); ++i) {
                        this.faceEntity((Entity)e, (float)Client.INSTANCE.getSettingsManager().getSettingByName("SmoothAimbot:Horizontal").getValDouble(), (float)Client.INSTANCE.getSettingsManager().getSettingByName("SmoothAimbot:Vertical").getValDouble());
                    }
                }
            }
        }
    }
    
    public void faceEntity(final Entity entityIn, final float p_70625_2_, final float p_70625_3_) {
        final double d0 = entityIn.posX - ModuleSmoothAimbot.mc.thePlayer.posX;
        final double d2 = entityIn.posZ - ModuleSmoothAimbot.mc.thePlayer.posZ;
        double d3;
        if (entityIn instanceof EntityLivingBase) {
            final EntityLivingBase entitylivingbase = (EntityLivingBase)entityIn;
            d3 = entitylivingbase.posY + entitylivingbase.getEyeHeight() - (ModuleSmoothAimbot.mc.thePlayer.posY + ModuleSmoothAimbot.mc.thePlayer.getEyeHeight());
        }
        else {
            d3 = (entityIn.getEntityBoundingBox().minY + entityIn.getEntityBoundingBox().maxY) / 2.0 - (ModuleSmoothAimbot.mc.thePlayer.posY + ModuleSmoothAimbot.mc.thePlayer.getEyeHeight());
        }
        final double d4 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);
        final float f = (float)(MathHelper.atan2(d2, d0) * 180.0 / 3.141592653589793) - 90.0f;
        final float f2 = (float)(-(MathHelper.atan2(d3, d4) * 180.0 / 3.141592653589793));
        ModuleSmoothAimbot.mc.thePlayer.rotationPitch = this.updateRotation(ModuleSmoothAimbot.mc.thePlayer.rotationPitch, f2, p_70625_3_);
        ModuleSmoothAimbot.mc.thePlayer.rotationYaw = this.updateRotation(ModuleSmoothAimbot.mc.thePlayer.rotationYaw, f, p_70625_2_);
    }
    
    private float updateRotation(final float p_70663_1_, final float p_70663_2_, final float p_70663_3_) {
        float f = MathHelper.wrapAngleTo180_float(p_70663_2_ - p_70663_1_);
        if (f > p_70663_3_) {
            f = p_70663_3_;
        }
        if (f < -p_70663_3_) {
            f = -p_70663_3_;
        }
        return p_70663_1_ + f;
    }
    
    private Entity rangeSwitch() {
        Entity nearest = null;
        for (final Object o : ModuleSmoothAimbot.mc.theWorld.loadedEntityList) {
            final Entity e = (Entity)o;
            if (e != ModuleSmoothAimbot.mc.thePlayer && e.isEntityAlive() && !e.isDead && e instanceof EntityLivingBase && (nearest == null || ModuleSmoothAimbot.mc.thePlayer.getDistanceToEntity(e) < ModuleSmoothAimbot.mc.thePlayer.getDistanceToEntity(nearest))) {
                nearest = e;
            }
        }
        return nearest;
    }
    
    private float getDistFromMouseSq(final EntityLivingBase entity) {
        final double diffX = entity.posX - ModuleSmoothAimbot.mc.thePlayer.posX;
        final double diffY = entity.posY + entity.getEyeHeight() * 0.9 - (ModuleSmoothAimbot.mc.thePlayer.posY + ModuleSmoothAimbot.mc.thePlayer.getEyeHeight());
        final double diffZ = entity.posZ - ModuleSmoothAimbot.mc.thePlayer.posZ;
        final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / 3.141592653589793));
        final float[] neededRotations = { ModuleSmoothAimbot.mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - ModuleSmoothAimbot.mc.thePlayer.rotationYaw), ModuleSmoothAimbot.mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - ModuleSmoothAimbot.mc.thePlayer.rotationPitch) };
        final float neededYaw = ModuleSmoothAimbot.mc.thePlayer.rotationYaw - neededRotations[0];
        final float neededPitch = ModuleSmoothAimbot.mc.thePlayer.rotationPitch - neededRotations[1];
        return neededYaw * neededYaw + neededPitch * neededPitch;
    }
    
    public static boolean getItem() {
        try {
            return Client.INSTANCE.getSettingsManager().getSettingByName("SmoothAimbot:Mode").getValString().equalsIgnoreCase("All Items") || (Client.INSTANCE.getSettingsManager().getSettingByName("SmoothAimbot:Mode").getValString().equalsIgnoreCase("Hand/Stick/Sword") && (ModuleSmoothAimbot.mc.thePlayer.getCurrentEquippedItem() == null || Item.getIdFromItem(ModuleSmoothAimbot.mc.thePlayer.getCurrentEquippedItem().getItem()) == 280 || ModuleSmoothAimbot.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword)) || (Client.INSTANCE.getSettingsManager().getSettingByName("SmoothAimbot:Mode").getValString().equalsIgnoreCase("Stick/Sword") && (Item.getIdFromItem(ModuleSmoothAimbot.mc.thePlayer.getCurrentEquippedItem().getItem()) == 280 || ModuleSmoothAimbot.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword));
        }
        catch (Exception e) {
            return false;
        }
    }
}
