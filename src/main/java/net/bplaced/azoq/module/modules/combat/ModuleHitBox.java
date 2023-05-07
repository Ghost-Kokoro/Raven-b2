package net.bplaced.azoq.module.modules.combat;

import net.bplaced.azoq.Client;
import net.bplaced.azoq.event.eventapi.EventTarget;
import net.bplaced.azoq.event.events.EventRunTick;
import net.bplaced.azoq.gui.click.settings.Setting;
import net.bplaced.azoq.module.Module;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;

@Module.Information(moduleName = "HitBox", displayName = "HitBox", category = Module.Category.COMBAT)
public class ModuleHitBox extends Module {
	
	@Override
    public void setup() {
        super.setup();
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("HitBox:Mode", (Module)this, "All Items", new String[] { "All Items", "Hand/Stick/Sword", "Stick/Sword" }));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("HitBox:HitBox", (Module)this, 20.0, 0.0, 200.0, true));
    }
    
    @EventTarget
    public void onRunTick(final EventRunTick event) {
        this.setDisplayName(this.getModuleName() + " \2477[" + (int)Client.INSTANCE.getSettingsManager().getSettingByName("HitBox:HitBox").getValDouble() + "]\247r");
    }
    
    public static boolean getItem() {
        try {
            return Client.INSTANCE.getSettingsManager().getSettingByName("HitBox:Mode").getValString().equalsIgnoreCase("All Items") || (Client.INSTANCE.getSettingsManager().getSettingByName("HitBox:Mode").getValString().equalsIgnoreCase("Hand/Stick/Sword") && (ModuleHitBox.mc.thePlayer.getCurrentEquippedItem() == null || Item.getIdFromItem(ModuleHitBox.mc.thePlayer.getCurrentEquippedItem().getItem()) == 280 || ModuleHitBox.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword)) || (Client.INSTANCE.getSettingsManager().getSettingByName("HitBox:Mode").getValString().equalsIgnoreCase("Stick/Sword") && (Item.getIdFromItem(ModuleHitBox.mc.thePlayer.getCurrentEquippedItem().getItem()) == 280 || ModuleHitBox.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword));
        }
        catch (Exception e) {
            return false;
        }
    }
}
