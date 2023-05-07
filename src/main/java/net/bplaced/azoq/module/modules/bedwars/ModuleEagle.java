package net.bplaced.azoq.module.modules.bedwars;

import net.bplaced.azoq.event.eventapi.EventTarget;
import net.bplaced.azoq.event.events.EventUpdate;
import net.bplaced.azoq.module.Module;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSnow;
import net.minecraft.util.BlockPos;

@Module.Information(moduleName = "Eagle", displayName = "Eagle", category = Module.Category.BEDWARS)
public class ModuleEagle extends Module {
    @EventTarget
    public void onUpdate(final EventUpdate event) {
        try {
            if (ModuleEagle.mc.currentScreen == null && this.getItem() && ModuleEagle.mc.theWorld.isAirBlock(new BlockPos(ModuleEagle.mc.thePlayer.posX, ModuleEagle.mc.thePlayer.posY - 1.0, ModuleEagle.mc.thePlayer.posZ))) {
                KeyBinding.setKeyBindState(ModuleEagle.mc.gameSettings.keyBindSneak.getKeyCode(), true);
            } else {
                KeyBinding.setKeyBindState(ModuleEagle.mc.gameSettings.keyBindSneak.getKeyCode(), false);
            }
        }
        catch (Exception ex) {}
    }
    
    private boolean getItem() {
        return ModuleEagle.mc.thePlayer.getCurrentEquippedItem() != null && !(ModuleEagle.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSnow) && Item.getIdFromItem(ModuleEagle.mc.thePlayer.getCurrentEquippedItem().getItem()) != 30 && ModuleEagle.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock;
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        KeyBinding.setKeyBindState(ModuleEagle.mc.gameSettings.keyBindSneak.getKeyCode(), false);
    }
}
