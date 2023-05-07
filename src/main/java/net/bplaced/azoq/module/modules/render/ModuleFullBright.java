package net.bplaced.azoq.module.modules.render;

import net.bplaced.azoq.event.eventapi.EventTarget;
import net.bplaced.azoq.event.events.EventUpdate;
import net.bplaced.azoq.module.Module;

@Module.Information(moduleName = "FullBright", displayName = "FullBright", category = Module.Category.RENDER)
public class ModuleFullBright extends Module {
    @EventTarget
    public void onUpdate(final EventUpdate event) {
        ModuleFullBright.mc.gameSettings.gammaSetting = 10.0f;
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        ModuleFullBright.mc.gameSettings.gammaSetting = 1.0f;
    }
}
