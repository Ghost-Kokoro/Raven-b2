package net.bplaced.azoq.module.modules.player;

import net.bplaced.azoq.event.eventapi.EventTarget;
import net.bplaced.azoq.event.events.EventUpdate;
import net.bplaced.azoq.module.Module;

@Module.Information(moduleName = "FastRespawn", displayName = "FastRespawn", category = Module.Category.PLAYER)
public class ModuleFastRespawn extends Module {
    @EventTarget
    public void onUpdate(final EventUpdate event) {
        if (!ModuleFastRespawn.mc.thePlayer.isEntityAlive()) {
            ModuleFastRespawn.mc.thePlayer.respawnPlayer();
        }
    }
}
