package net.bplaced.azoq.module.modules.movement;

import net.bplaced.azoq.Client;
import net.bplaced.azoq.event.eventapi.EventTarget;
import net.bplaced.azoq.event.events.EventRunTick;
import net.bplaced.azoq.event.events.EventUpdate;
import net.bplaced.azoq.gui.click.settings.Setting;
import net.bplaced.azoq.module.Module;

@Module.Information(moduleName = "Velocity", displayName = "Velocity", category = Module.Category.MOVEMENT)
public class ModuleVelocity extends Module {
    public static boolean velocity;
    
    @Override
    public void setup() {
        super.setup();
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("Velocity:Mode", (Module)this, "NCP", new String[] { "NCP", "Legit AAC" }));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("Velocity:NCP XZ", (Module)this, 100.0, 0.0, 200.0, true));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("Velocity:NCP Y", (Module)this, 100.0, 0.0, 200.0, true));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("Velocity:NCP Fight", (Module)this, false));
    }
    
    @EventTarget
    public void onRunTick(final EventRunTick event) {
        if (Client.INSTANCE.getSettingsManager().getSettingByName("Velocity:Mode").getValString().equalsIgnoreCase("NCP")) {
            this.setDisplayName(this.getModuleName() + " \2477[" + "NCP | X/Z:" + (int)Client.INSTANCE.getSettingsManager().getSettingByName("Velocity:NCP XZ").getValDouble() + " | Y:" + (int)Client.INSTANCE.getSettingsManager().getSettingByName("Velocity:NCP Y").getValDouble() + "]\247r");
        }
        else if (Client.INSTANCE.getSettingsManager().getSettingByName("Velocity:Mode").getValString().equalsIgnoreCase("Legit AAC")) {
            this.setDisplayName(this.getModuleName() + " \2477[" + "Legit AAC" + "]\247r");
        }
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate event) {
        try {
            if (Client.INSTANCE.getSettingsManager().getSettingByName("Velocity:Mode").getValString().equalsIgnoreCase("NCP") && Client.INSTANCE.getSettingsManager().getSettingByName("Velocity:NCP Fight").getValBoolean()) {
                if (ModuleVelocity.mc.thePlayer.isSwingInProgress) {
                    ModuleVelocity.velocity = true;
                }
                else {
                    ModuleVelocity.velocity = false;
                }
            }
            else {
                ModuleVelocity.velocity = true;
            }
        }
        catch (Exception e) {
            ModuleVelocity.velocity = false;
        }
    }
    
    static {
        ModuleVelocity.velocity = false;
    }
}
