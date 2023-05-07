package net.bplaced.azoq.module.modules.settings;

import net.bplaced.azoq.Client;
import net.bplaced.azoq.gui.click.settings.Setting;
import net.bplaced.azoq.module.Module;

@Module.Information(moduleName = "ArrayList", displayName = "ArrayList", category = Module.Category.SETTINGS, visible = false)
public class ModuleArrayList extends Module {
	@Override
    public void setup() {
        super.setup();
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("ArrayList:Side", (Module)this, "Left", new String[] { "Left", "Right" }));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("ArrayList:Rect", (Module)this, true));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("ArrayList:Info", (Module)this, true));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("ArrayList:Red", (Module)this, 255.0, 0.0, 255.0, true));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("ArrayList:Green", (Module)this, 255.0, 0.0, 255.0, true));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("ArrayList:Blue", (Module)this, 255.0, 0.0, 255.0, true));
    }
    
	@Override
    public void onEnable() {
        super.onEnable();
        this.toggleModule();
    }
}
