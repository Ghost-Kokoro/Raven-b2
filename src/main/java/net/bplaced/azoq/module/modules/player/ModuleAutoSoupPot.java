package net.bplaced.azoq.module.modules.player;

import net.bplaced.azoq.Client;
import net.bplaced.azoq.event.eventapi.EventTarget;
import net.bplaced.azoq.event.events.EventRunTick;
import net.bplaced.azoq.event.events.EventUpdate;
import net.bplaced.azoq.gui.click.settings.Setting;
import net.bplaced.azoq.module.Module;
import net.bplaced.azoq.utils.InventoryUtils;
import net.bplaced.azoq.utils.TimeUtils;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

@Module.Information(moduleName = "AutoSoupPot", displayName = "AutoSoupPot", category = Module.Category.PLAYER)
public class ModuleAutoSoupPot extends Module {
    private TimeUtils time;
    private TimeUtils time2;
    private Item item;
    private boolean back;
    
    public ModuleAutoSoupPot() {
        this.time = new TimeUtils();
        this.time2 = new TimeUtils();
        this.item = null;
        this.back = false;
    }
    
    @Override
    public void setup() {
        super.setup();
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("AutoSoupPot:Mode", (Module)this, "Soup", new String[] { "Soup", "Pot" }));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("AutoSoupPot:Health", (Module)this, 15.0, 0.0, 20.0, true));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("AutoSoupPot:Delay", (Module)this, 250.0, 1.0, 1000.0, true));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("AutoSoupPot:SwitchBack Delay", (Module)this, 125.0, 1.0, 1000.0, true));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("AutoSoupPot:Drop", (Module)this, true));
    }
    
    @EventTarget
    public void onRunTick(final EventRunTick event) {
        this.setDisplayName(this.getModuleName() + " \2477[" + Client.INSTANCE.getSettingsManager().getSettingByName("AutoSoupPot:Mode").getValString().substring(0, 1).toUpperCase() + Client.INSTANCE.getSettingsManager().getSettingByName("AutoSoupPot:Mode").getValString().substring(1, Client.INSTANCE.getSettingsManager().getSettingByName("AutoSoupPot:Mode").getValString().length()).toLowerCase() + " | " + (int)Client.INSTANCE.getSettingsManager().getSettingByName("AutoSoupPot:Health").getValDouble() + '\u2764' + " | " + (int)Client.INSTANCE.getSettingsManager().getSettingByName("AutoSoupPot:Delay").getValDouble() + "ms/" + (int)Client.INSTANCE.getSettingsManager().getSettingByName("AutoSoupPot:SwitchBack Delay").getValDouble() + "ms" + "]\247r");
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate event) {
        if (ModuleAutoSoupPot.mc.currentScreen != null) {
            return;
        }
        if (!this.back && ModuleAutoSoupPot.mc.thePlayer.getHealth() <= Client.INSTANCE.getSettingsManager().getSettingByName("AutoSoupPot:Health").getValDouble() && this.time.hasReached(Client.INSTANCE.getSettingsManager().getSettingByName("AutoSoupPot:Delay").getValDouble())) {
            this.eat();
            this.resetTime();
        }
        if (this.back && this.time2.hasReached(Client.INSTANCE.getSettingsManager().getSettingByName("AutoSoupPot:SwitchBack Delay").getValDouble())) {
            this.back();
            this.resetTime();
        }
    }
    
    private void eat() {
        if (Client.INSTANCE.getSettingsManager().getSettingByName("AutoSoupPot:Mode").getValString().equalsIgnoreCase("Soup")) {
            this.item = Items.mushroom_stew;
        }
        if (Client.INSTANCE.getSettingsManager().getSettingByName("AutoSoupPot:Mode").getValString().equalsIgnoreCase("Pot")) {
            this.item = (Item)Items.potionitem;
        }
        final int soup = InventoryUtils.findItemInHot(this.item);
        if (soup == -1) {
            return;
        }
        ModuleAutoSoupPot.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C09PacketHeldItemChange(soup));
        ModuleAutoSoupPot.mc.thePlayer.inventory.currentItem = soup;
        ModuleAutoSoupPot.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C08PacketPlayerBlockPlacement(ModuleAutoSoupPot.mc.thePlayer.inventoryContainer.getSlot(soup).getStack()));
        if (Client.INSTANCE.getSettingsManager().getSettingByName("AutoSoupPot:Drop").getValBoolean()) {
            ModuleAutoSoupPot.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.DROP_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        }
        this.back = true;
    }
    
    private void back() {
        final int slot = InventoryUtils.getBestWeapon((Entity)ModuleAutoSoupPot.mc.thePlayer);
        ModuleAutoSoupPot.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C09PacketHeldItemChange(slot));
        ModuleAutoSoupPot.mc.thePlayer.inventory.currentItem = slot;
        this.back = false;
    }
    
    private void resetTime() {
        this.time.reset();
        this.time2.reset();
    }
}
