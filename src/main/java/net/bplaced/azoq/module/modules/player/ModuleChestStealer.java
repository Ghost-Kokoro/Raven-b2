package net.bplaced.azoq.module.modules.player;

import net.bplaced.azoq.Client;
import net.bplaced.azoq.event.eventapi.EventTarget;
import net.bplaced.azoq.event.events.EventRunTick;
import net.bplaced.azoq.event.events.EventUpdate;
import net.bplaced.azoq.gui.click.settings.Setting;
import net.bplaced.azoq.module.Module;
import net.bplaced.azoq.utils.TimeUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;

@Module.Information(moduleName = "ChestStealer", displayName = "ChestStealer", category = Module.Category.PLAYER)
public class ModuleChestStealer extends Module {
    private TimeUtils time;
    
    public ModuleChestStealer() {
        this.time = new TimeUtils();
    }
    
    @Override
    public void setup() {
        super.setup();
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("ChestStealer:Delay", (Module)this, 50.0, 0.0, 250.0, true));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("ChestStealer:Auto Close", (Module)this, true));
    }
    
    @EventTarget
    public void onRunTick(final EventRunTick event) {
        this.setDisplayName(this.getModuleName() + " \2477[" + (int)Client.INSTANCE.getSettingsManager().getSettingByName("ChestStealer:Delay").getValDouble() + "ms" + "]\247r");
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate event) {
        final Container chest = ModuleChestStealer.mc.thePlayer.openContainer;
        if (chest != null && chest instanceof ContainerChest) {
            final ContainerChest container = (ContainerChest)chest;
            for (int i = 0; i < container.getLowerChestInventory().getSizeInventory(); ++i) {
                if (container.getLowerChestInventory().getStackInSlot(i) != null && this.time.hasReached(Client.INSTANCE.getSettingsManager().getSettingByName("ChestStealer:Delay").getValDouble())) {
                    ModuleChestStealer.mc.playerController.windowClick(container.windowId, i, 0, 1, (EntityPlayer)ModuleChestStealer.mc.thePlayer);
                    this.time.reset();
                }
            }
            if (this.isContainerEmpty(chest) && Client.INSTANCE.getSettingsManager().getSettingByName("ChestStealer:Auto Close").getValBoolean()) {
                ModuleChestStealer.mc.thePlayer.closeScreen();
            }
        }
    }
    
    private boolean isContainerEmpty(final Container container) {
        boolean temp = true;
        for (int i = 0, slotAmount = (container.inventorySlots.size() == 90) ? 54 : 27; i < slotAmount; ++i) {
            if (container.getSlot(i).getHasStack()) {
                temp = false;
            }
        }
        return temp;
    }
}
