package net.bplaced.azoq.module.modules.movement;

import net.bplaced.azoq.event.eventapi.EventTarget;
import net.bplaced.azoq.event.events.EventRunTick;
import net.bplaced.azoq.module.Module;
import net.minecraft.entity.Entity;

@Module.Information(moduleName = "Parkour", displayName = "Parkour", category = Module.Category.MOVEMENT)
public class ModuleParkour extends Module {
    @EventTarget
    public void onRunTick(final EventRunTick event) {
        if (ModuleParkour.mc.currentScreen == null && ModuleParkour.mc.theWorld.getCollidingBoundingBoxes((Entity)ModuleParkour.mc.thePlayer, ModuleParkour.mc.thePlayer.getEntityBoundingBox().offset(0.0, -0.5, 0.0).expand(0.001, 0.0, 0.001)).isEmpty() && ModuleParkour.mc.thePlayer.onGround && !ModuleParkour.mc.thePlayer.isSneaking() && ModuleParkour.mc.gameSettings.keyBindForward.isKeyDown()) {
            ModuleParkour.mc.thePlayer.jump();
        }
    }
}
