package net.bplaced.azoq.module.modules.combat;

import net.bplaced.azoq.Client;
import net.bplaced.azoq.gui.click.settings.Setting;
import net.bplaced.azoq.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

@Module.Information(moduleName = "AntiBots", displayName = "AntiBots", category = Module.Category.COMBAT)
public class ModuleAntiBots extends Module {
	
	@Override
    public void setup() {
        super.setup();
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("AntiBots:Invisibles", (Module)this, true));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("AntiBots:OnlyPlayers", (Module)this, true));
    }
    
    public static boolean isBot(final Entity e) {
        return Client.INSTANCE.getModuleManager().getModule((Class)ModuleAntiBots.class).isEnabled() && ((e.isInvisible() && Client.INSTANCE.getSettingsManager().getSettingByName("AntiBots:Invisibles").getValBoolean()) || (!(e instanceof EntityPlayer) && Client.INSTANCE.getSettingsManager().getSettingByName("AntiBots:OnlyPlayers").getValBoolean()) || e.isDead || !e.isEntityAlive() || ((EntityLivingBase)e).getHealth() < 0.0f || ((EntityLivingBase)e).deathTime > 0 || e == ModuleAntiBots.mc.thePlayer || e.getUniqueID() == null);
    }
}
