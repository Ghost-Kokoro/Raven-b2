package net.bplaced.azoq.config.configs;

import net.bplaced.azoq.Client;
import net.bplaced.azoq.gui.click.settings.Setting;
import net.bplaced.azoq.module.Module;
import net.bplaced.azoq.module.Module.Category;
import net.bplaced.azoq.module.Module.Information;
import net.bplaced.azoq.module.modules.render.ModuleNotifications;
import net.bplaced.azoq.utils.ChatUtils;

@Information(moduleName = "Configs", displayName = "Configs", category = Category.SETTINGS, visible = false)
public class ModuleConfigs extends Module {
    private boolean isConfigEnable;
    
    public ModuleConfigs() {
        this.isConfigEnable = false;
    }
    
    @Override
    public void setup() {
        final String[] config = new String[Client.INSTANCE.getConfigManager().getConfigs().size()];
        for (int i = 0; i < Client.INSTANCE.getConfigManager().getConfigs().size(); ++i) {
            config[i] = Client.INSTANCE.getConfigManager().getConfigs().get(i);
        }
        if (config == null || config.length == 0) {
            return;
        }
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("Configs:Configs", this, config[0], config));
        this.isConfigEnable = true;
        super.setup();
    }
    
    @Override
    public void onEnable() {
        if (this.isConfigEnable) {
            final String configName = Client.INSTANCE.getSettingsManager().getSettingByName("Configs:Configs").getValString();
            Client.INSTANCE.getConfigManager().loadConfig(configName);
            this.toggleModule();
            super.onEnable();
        }
        else {
            if (Client.INSTANCE.getModuleManager().getModule(ModuleNotifications.class).isEnabled() && Client.INSTANCE.getSettingsManager().getSettingByName("ModuleNotifications:Config").getValBoolean()) {
                ChatUtils.sendChatMessage("Keine Config vorhanden!");
            }
            this.toggleModule();
        }
    }
}
