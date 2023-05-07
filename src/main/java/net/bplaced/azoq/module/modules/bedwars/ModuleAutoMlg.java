package net.bplaced.azoq.module.modules.bedwars;

import net.bplaced.azoq.Client;
import net.bplaced.azoq.event.eventapi.EventTarget;
import net.bplaced.azoq.event.events.EventUpdate;
import net.bplaced.azoq.gui.click.settings.Setting;
import net.bplaced.azoq.module.Module;
import net.bplaced.azoq.utils.ReflectionUtils;
import net.minecraft.item.Item;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;

@Module.Information(moduleName = "AutoMlg", displayName = "AutoMlg", category = Module.Category.BEDWARS)
public class ModuleAutoMlg extends Module {
	
	@Override
    public void setup() {
        super.setup();
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("AutoMlg:Cobweb", (Module)this, true));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("AutoMlg:Water", (Module)this, true));
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate event) {
        try {
            if (ModuleAutoMlg.mc.currentScreen == null && ModuleAutoMlg.mc.thePlayer.fallDistance > 4.0f && !ModuleAutoMlg.mc.thePlayer.isOnLadder() && this.getItem() && ModuleAutoMlg.mc.objectMouseOver.typeOfHit == MovingObjectType.BLOCK) {
                ReflectionUtils.rightClickMouse();
                ModuleAutoMlg.mc.thePlayer.fallDistance = 0.0f;
            }
        }
        catch (Exception ex) {}
    }
    
    private boolean getItem() {
        return ModuleAutoMlg.mc.thePlayer.getCurrentEquippedItem() != null && ((Client.INSTANCE.getSettingsManager().getSettingByName("AutoMlg:Cobweb").getValBoolean() && Item.getIdFromItem(ModuleAutoMlg.mc.thePlayer.getCurrentEquippedItem().getItem()) == 30) || (Client.INSTANCE.getSettingsManager().getSettingByName("AutoMlg:Water").getValBoolean() && Item.getIdFromItem(ModuleAutoMlg.mc.thePlayer.getCurrentEquippedItem().getItem()) == 326));
    }
}
