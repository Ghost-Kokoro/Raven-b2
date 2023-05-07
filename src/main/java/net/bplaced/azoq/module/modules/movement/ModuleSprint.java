package net.bplaced.azoq.module.modules.movement;

import net.bplaced.azoq.event.eventapi.EventTarget;
import net.bplaced.azoq.event.events.EventUpdate;
import net.bplaced.azoq.module.Module;
import net.minecraft.client.settings.KeyBinding;

@Module.Information(moduleName = "Sprint", displayName = "Sprint", category = Module.Category.MOVEMENT)
public class ModuleSprint extends Module {
    @EventTarget
    public void onUpdate(final EventUpdate event) {
        if (!ModuleSprint.mc.thePlayer.isSprinting()) {
            KeyBinding.setKeyBindState(ModuleSprint.mc.gameSettings.keyBindSprint.getKeyCode(), true);
        }
    }
    
    public void onDisable() {
        super.onDisable();
        if (ModuleSprint.mc.thePlayer != null && ModuleSprint.mc.thePlayer.isSprinting()) {
            KeyBinding.setKeyBindState(ModuleSprint.mc.gameSettings.keyBindSprint.getKeyCode(), false);
        }
    }
}
