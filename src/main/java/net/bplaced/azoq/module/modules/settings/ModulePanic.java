package net.bplaced.azoq.module.modules.settings;

import net.bplaced.azoq.module.Module;
import net.bplaced.azoq.utils.ChatUtils;

@Module.Information(moduleName = "Panic", displayName = "Panic", category = Module.Category.SETTINGS, visible = false)
public class ModulePanic extends Module {
	@Override
    public void onEnable() {
        super.onEnable();
        ChatUtils.sendChatMessage("Panic J/N ?");
    }
}
