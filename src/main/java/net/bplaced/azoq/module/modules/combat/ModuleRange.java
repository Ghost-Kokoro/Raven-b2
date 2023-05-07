package net.bplaced.azoq.module.modules.combat;

import java.util.Iterator;
import net.bplaced.azoq.Client;
import net.bplaced.azoq.event.eventapi.EventTarget;
import net.bplaced.azoq.event.events.EventRunTick;
import net.bplaced.azoq.gui.click.settings.Setting;
import net.bplaced.azoq.module.Module;
import net.bplaced.azoq.utils.MathUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;

@Module.Information(moduleName = "Range", displayName = "Range", category = Module.Category.COMBAT)
public class ModuleRange extends Module {
	
	@Override
    public void setup() {
        super.setup();
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("Range:Mode", (Module)this, "All Items", new String[] { "All Items", "Hand/Stick/Sword", "Stick/Sword" }));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("Range:Range", (Module)this, 4.5, 0.0, 10.0, false));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("Range:Combo Mode", (Module)this, false));
    }
    
    @EventTarget
    public void onRunTick(final EventRunTick event) {
        this.setDisplayName(this.getModuleName() + " \2477[" + MathUtils.round(Client.INSTANCE.getSettingsManager().getSettingByName("Range:Range").getValDouble()) + "]\247r");
    }
    
    public static boolean getItem() {
        try {
            if (Client.INSTANCE.getSettingsManager().getSettingByName("Range:Combo Mode").getValBoolean()) {
                for (final Entity e : mc.theWorld.loadedEntityList) {
                    if (e != mc.thePlayer && e instanceof EntityPlayer && e.hurtResistantTime == 0) {
                        return false;
                    }
                }
            }
            return Client.INSTANCE.getSettingsManager().getSettingByName("Range:Mode").getValString().equalsIgnoreCase("All Items") || (Client.INSTANCE.getSettingsManager().getSettingByName("Range:Mode").getValString().equalsIgnoreCase("Hand/Stick/Sword") && (ModuleRange.mc.thePlayer.getCurrentEquippedItem() == null || Item.getIdFromItem(mc.thePlayer.getCurrentEquippedItem().getItem()) == 280 || mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword)) || (Client.INSTANCE.getSettingsManager().getSettingByName("Range:Mode").getValString().equalsIgnoreCase("Stick/Sword") && (Item.getIdFromItem(mc.thePlayer.getCurrentEquippedItem().getItem()) == 280 || mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword));
        } catch (Exception e2) {
            return false;
        }
    }
}
