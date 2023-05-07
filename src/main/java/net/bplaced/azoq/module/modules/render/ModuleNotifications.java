package net.bplaced.azoq.module.modules.render;

import net.bplaced.azoq.Client;
import net.bplaced.azoq.gui.click.settings.Setting;
import net.bplaced.azoq.module.Module;

@Module.Information(moduleName = "Notifications", displayName = "Notifications", category = Module.Category.RENDER)
public class ModuleNotifications extends Module {
	@Override
    public void setup() {
        super.setup();
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("ModuleNotifications:KeyBinds", (Module)this, true));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("ModuleNotifications:Friends", (Module)this, true));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("ModuleNotifications:Config", (Module)this, true));
    }
}
