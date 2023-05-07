package net.bplaced.azoq.module.modules.combat;

import net.bplaced.azoq.Client;
import net.bplaced.azoq.event.eventapi.EventTarget;
import net.bplaced.azoq.event.events.EventRunTick;
import net.bplaced.azoq.event.events.EventUpdate;
import net.bplaced.azoq.gui.click.settings.Setting;
import net.bplaced.azoq.module.Module;
import net.bplaced.azoq.utils.ReflectionUtils;
import net.bplaced.azoq.utils.TimeUtils;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFishingRod;

@Module.Information(moduleName = "AutoClicker", displayName = "AutoClicker", category = Module.Category.COMBAT)
public class ModuleAutoClicker extends Module {
    private TimeUtils time;
    private TimeUtils time1;
    
    public ModuleAutoClicker() {
        this.time = new TimeUtils();
        this.time1 = new TimeUtils();
    }
    
    @Override
    public void setup() {
        super.setup();
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("AutoClicker:Mode", (Module)this, "Left/Right", new String[] { "Left", "Right", "Left/Right" }));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("AutoClicker:Left Delay", (Module)this, 250.0, 1.0, 1000.0, true));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("AutoClicker:Right Delay", (Module)this, 250.0, 1.0, 500.0, true));
    }
    
    @EventTarget
    public void onRunTick(final EventRunTick event) {
        if (Client.INSTANCE.getSettingsManager().getSettingByName("AutoClicker:Mode").getValString().equalsIgnoreCase("Left")) {
            this.setDisplayName(this.getModuleName() + " \2477[" + "L:" + (int)Client.INSTANCE.getSettingsManager().getSettingByName("AutoClicker:Left Delay").getValDouble() + "ms" + "]\247r");
        }
        else if (Client.INSTANCE.getSettingsManager().getSettingByName("AutoClicker:Mode").getValString().equalsIgnoreCase("Right")) {
            this.setDisplayName(this.getModuleName() + " \2477[" + "R:" + (int)Client.INSTANCE.getSettingsManager().getSettingByName("AutoClicker:Right Delay").getValDouble() + "ms" + "]\247r");
        }
        else if (Client.INSTANCE.getSettingsManager().getSettingByName("AutoClicker:Mode").getValString().equalsIgnoreCase("Left/Right")) {
            this.setDisplayName(this.getModuleName() + " \2477[" + "L:" + (int)Client.INSTANCE.getSettingsManager().getSettingByName("AutoClicker:Left Delay").getValDouble() + "ms " + "| R:" + (int)Client.INSTANCE.getSettingsManager().getSettingByName("AutoClicker:Right Delay").getValDouble() + "ms" + "]\247r");
        }
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate event) {
        if (Client.INSTANCE.getSettingsManager().getSettingByName("AutoClicker:Mode").getValString().equalsIgnoreCase("Left")) {
            this.left();
        }
        else if (Client.INSTANCE.getSettingsManager().getSettingByName("AutoClicker:Mode").getValString().equalsIgnoreCase("Right")) {
            this.right();
        }
        else if (Client.INSTANCE.getSettingsManager().getSettingByName("AutoClicker:Mode").getValString().equalsIgnoreCase("Left/Right")) {
            this.left();
            this.right();
        }
    }
    
    private void left() {
        if (ModuleAutoClicker.mc.gameSettings.keyBindAttack.isKeyDown() && this.time.hasReached(Client.INSTANCE.getSettingsManager().getSettingByName("AutoClicker:Left Delay").getValDouble())) {
            ReflectionUtils.setLeftClickCounter(0);
            ReflectionUtils.clickMouse();
            this.time.reset();
        }
    }
    
    private void right() {
        if (ModuleAutoClicker.mc.gameSettings.keyBindUseItem.isKeyDown() && this.getItem() && this.time1.hasReached(Client.INSTANCE.getSettingsManager().getSettingByName("AutoClicker:Right Delay").getValDouble())) {
            ReflectionUtils.setRightClickDelayTimer(0);
            ReflectionUtils.rightClickMouse();
            this.time1.reset();
        }
    }
    
    private boolean getItem() {
        try {
            return ModuleAutoClicker.mc.thePlayer.getCurrentEquippedItem() != null && !(ModuleAutoClicker.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemFishingRod) && !(ModuleAutoClicker.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow) && !ModuleAutoClicker.mc.thePlayer.isEating();
        }
        catch (Exception e) {
            return false;
        }
    }
}
