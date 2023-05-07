package net.bplaced.azoq.module.modules.movement;

import net.bplaced.azoq.Client;
import net.bplaced.azoq.gui.click.settings.Setting;
import net.bplaced.azoq.module.Module;

@Module.Information(moduleName = "KeepSprint", displayName = "KeepSprint", category = Module.Category.MOVEMENT)
public class ModuleKeepSprint extends Module {
    public static boolean fakeFOV;
    
    @Override
    public void setup() {
        super.setup();
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("KeepSprint:FakeFOV", (Module)this, true));
    }
    
    static {
        ModuleKeepSprint.fakeFOV = false;
    }
}
