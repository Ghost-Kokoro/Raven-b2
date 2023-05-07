package net.bplaced.azoq.config.configs;

import net.bplaced.azoq.module.Module;
import net.bplaced.azoq.module.Module.Information;
import net.bplaced.azoq.utils.ChatUtils;
import net.bplaced.azoq.module.Module.Category;

@Information(moduleName = "SaveConfig", displayName = "SaveConfig", category = Category.SETTINGS, visible = false)
public class ModuleSaveConfig extends Module {
	
    @Override
    public void onEnable() {
        super.onEnable();
        ChatUtils.sendChatMessage("Name der Config ?");
    }
}
