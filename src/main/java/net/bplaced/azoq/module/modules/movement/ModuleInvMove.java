package net.bplaced.azoq.module.modules.movement;

import net.bplaced.azoq.event.eventapi.EventTarget;
import net.bplaced.azoq.event.events.EventUpdate;
import net.bplaced.azoq.module.Module;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

@Module.Information(moduleName = "InvMove", displayName = "InvMove", category = Module.Category.MOVEMENT)
public class ModuleInvMove extends Module {
    @EventTarget
    public void onUpdate(final EventUpdate event) {
        final KeyBinding[] keys = { ModuleInvMove.mc.gameSettings.keyBindForward, ModuleInvMove.mc.gameSettings.keyBindBack, ModuleInvMove.mc.gameSettings.keyBindLeft, ModuleInvMove.mc.gameSettings.keyBindRight, ModuleInvMove.mc.gameSettings.keyBindJump };
        if (ModuleInvMove.mc.currentScreen != null && !(ModuleInvMove.mc.currentScreen instanceof GuiChat)) {
            for (int i = 0; i < keys.length; ++i) {
                KeyBinding.setKeyBindState(keys[i].getKeyCode(), Keyboard.isKeyDown(keys[i].getKeyCode()));
            }
        }
    }
}
