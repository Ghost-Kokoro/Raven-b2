package net.bplaced.azoq.module.modules.player;

import net.bplaced.azoq.Client;
import net.bplaced.azoq.event.eventapi.EventTarget;
import net.bplaced.azoq.event.events.EventRunTick;
import net.bplaced.azoq.event.events.EventUpdate;
import net.bplaced.azoq.gui.click.settings.Setting;
import net.bplaced.azoq.module.Module;
import net.bplaced.azoq.utils.InventoryUtils;
import net.bplaced.azoq.utils.TimeUtils;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;

@Module.Information(moduleName = "Refill", displayName = "Refill", category = Module.Category.PLAYER)
public class ModuleRefill extends Module {
    private TimeUtils time;
    private Item i;
    
    public ModuleRefill() {
        this.time = new TimeUtils();
        this.i = null;
    }
    
    @Override
    public void setup() {
        super.setup();
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("Refill:Mode", (Module)this, "Soup", new String[] { "Soup", "Pot" }));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("Refill:Open Inv", (Module)this, true));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("Refill:Delay", (Module)this, 500.0, 1.0, 2000.0, true));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("Refill:Auto Close", (Module)this, false));
    }
    
    @EventTarget
    public void onRunTick(final EventRunTick event) {
        this.setDisplayName(this.getModuleName() + " \2477[" + Client.INSTANCE.getSettingsManager().getSettingByName("Refill:Mode").getValString().substring(0, 1).toUpperCase() + Client.INSTANCE.getSettingsManager().getSettingByName("Refill:Mode").getValString().substring(1, Client.INSTANCE.getSettingsManager().getSettingByName("Refill:Mode").getValString().length()).toLowerCase() + " | " + (int)Client.INSTANCE.getSettingsManager().getSettingByName("Refill:Delay").getValDouble() + "ms" + "]\247r");
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate event) {
        if (Client.INSTANCE.getSettingsManager().getSettingByName("Refill:Mode").getValString().equalsIgnoreCase("Soup")) {
            this.i = Items.mushroom_stew;
        }
        else if (Client.INSTANCE.getSettingsManager().getSettingByName("Refill:Mode").getValString().equalsIgnoreCase("Pot")) {
            final ItemPotion potionitem = Items.potionitem;
            this.i = ItemPotion.getItemById(373);
        }
        this.refill();
    }
    
    private void refill() {
        if (Client.INSTANCE.getSettingsManager().getSettingByName("Refill:Open Inv").getValBoolean() && !(ModuleRefill.mc.currentScreen instanceof GuiInventory)) {
            return;
        }
        if (this.time.hasReached(Client.INSTANCE.getSettingsManager().getSettingByName("Refill:Delay").getValDouble())) {
            InventoryUtils.refill(this.i);
            this.time.reset();
        }
    }
}
