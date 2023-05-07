package net.bplaced.azoq.config.configs;

import net.bplaced.azoq.module.Module;
import net.bplaced.azoq.module.Module.Category;
import net.bplaced.azoq.module.Module.Information;
import net.bplaced.azoq.utils.ChatUtils;

@Information(moduleName = "RemoveConfig", displayName = "RemoveConfig", category = Category.SETTINGS, visible = false)
public class ModuleRemoveConfig extends Module {
    @Override
    public void onEnable() {
        super.onEnable();
        ChatUtils.sendChatMessage("Name der Config ?");
    }
}
