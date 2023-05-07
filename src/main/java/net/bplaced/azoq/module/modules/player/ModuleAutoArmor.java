package net.bplaced.azoq.module.modules.player;

import net.bplaced.azoq.Client;
import net.bplaced.azoq.event.eventapi.EventTarget;
import net.bplaced.azoq.event.events.EventRunTick;
import net.bplaced.azoq.event.events.EventUpdate;
import net.bplaced.azoq.gui.click.settings.Setting;
import net.bplaced.azoq.module.Module;
import net.bplaced.azoq.utils.TimeUtils;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@Module.Information(moduleName = "AutoArmor", displayName = "AutoArmor", category = Module.Category.PLAYER)
public class ModuleAutoArmor extends Module {
    private TimeUtils time;
    private final int[] boots;
    private final int[] chestplate;
    private final int[] helmet;
    private final int[] leggings;
    
    public ModuleAutoArmor() {
        this.time = new TimeUtils();
        this.boots = new int[] { 313, 309, 305, 317, 301 };
        this.chestplate = new int[] { 311, 307, 303, 315, 299 };
        this.helmet = new int[] { 310, 306, 302, 314, 298 };
        this.leggings = new int[] { 312, 308, 304, 316, 300 };
    }
    
    @Override
    public void setup() {
        super.setup();
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("AutoArmor:Delay", (Module)this, 500.0, 0.0, 1000.0, true));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("AutoArmor:Open Inv", (Module)this, true));
    }
    
    @EventTarget
    public void onRunTick(final EventRunTick event) {
        this.setDisplayName(this.getModuleName() + " \2477[" + (int)Client.INSTANCE.getSettingsManager().getSettingByName("AutoArmor:Delay").getValDouble() + "ms" + "]\247r");
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate event) {
        if (Client.INSTANCE.getSettingsManager().getSettingByName("AutoArmor:Open Inv").getValBoolean() && !(ModuleAutoArmor.mc.currentScreen instanceof GuiInventory)) {
            return;
        }
        if (!this.time.hasReached(Client.INSTANCE.getSettingsManager().getSettingByName("AutoArmor:Delay").getValDouble())) {
            return;
        }
        this.time.reset();
        if (ModuleAutoArmor.mc.thePlayer.openContainer != null && ModuleAutoArmor.mc.thePlayer.openContainer.windowId != 0 && ModuleAutoArmor.mc.currentScreen != null && !(ModuleAutoArmor.mc.currentScreen instanceof GuiInventory)) {
            return;
        }
        int item = -1;
        if (ModuleAutoArmor.mc.thePlayer.inventory.armorInventory[0] == null) {
            for (final int id : this.boots) {
                if (this.findItem(id) != -1) {
                    item = this.findItem(id);
                    break;
                }
            }
        }
        if (this.isArmorBetter(0, this.boots)) {
            item = 8;
        }
        if (ModuleAutoArmor.mc.thePlayer.inventory.armorInventory[3] == null) {
            for (final int id : this.helmet) {
                if (this.findItem(id) != -1) {
                    item = this.findItem(id);
                    break;
                }
            }
        }
        if (this.isArmorBetter(3, this.helmet)) {
            item = 5;
        }
        if (ModuleAutoArmor.mc.thePlayer.inventory.armorInventory[1] == null) {
            for (final int id : this.leggings) {
                if (this.findItem(id) != -1) {
                    item = this.findItem(id);
                    break;
                }
            }
        }
        if (this.isArmorBetter(1, this.leggings)) {
            item = 7;
        }
        if (ModuleAutoArmor.mc.thePlayer.inventory.armorInventory[2] == null) {
            for (final int id : this.chestplate) {
                if (this.findItem(id) != -1) {
                    item = this.findItem(id);
                    break;
                }
            }
        }
        if (this.isArmorBetter(2, this.chestplate)) {
            item = 6;
        }
        for (final ItemStack stack : ModuleAutoArmor.mc.thePlayer.inventory.mainInventory) {
            if (stack == null) {
                break;
            }
        }
        final boolean drop = false;
        if (item != -1) {
            ModuleAutoArmor.mc.playerController.windowClick(0, item, 0, drop ? 4 : 1, (EntityPlayer)ModuleAutoArmor.mc.thePlayer);
            this.time.reset();
        }
    }
    
    public boolean isArmorBetter(final int slot, final int[] armourtype) {
        if (ModuleAutoArmor.mc.thePlayer.inventory.armorInventory[slot] != null) {
            int currentSlot = 0;
            int finalSlot = -1;
            int invSlot = 0;
            int finalInvSlot = -1;
            for (final int armour : armourtype) {
                if (Item.getIdFromItem(ModuleAutoArmor.mc.thePlayer.inventory.armorInventory[slot].getItem()) == armour) {
                    finalSlot = currentSlot;
                    break;
                }
                ++currentSlot;
            }
            for (int i = 0; i < armourtype.length; ++i) {
                final int armour2 = armourtype[i];
                if (this.findItem(armour2) != -1) {
                    finalInvSlot = invSlot;
                    break;
                }
                ++invSlot;
            }
            if (finalInvSlot > -1) {
                return finalInvSlot < finalSlot;
            }
        }
        return false;
    }
    
    private int findItem(final int id) {
        for (int slot = 9; slot < 45; ++slot) {
            final ItemStack item = ModuleAutoArmor.mc.thePlayer.inventoryContainer.getSlot(slot).getStack();
            if (item != null && Item.getIdFromItem(item.getItem()) == id) {
                return slot;
            }
        }
        return -1;
    }
}
