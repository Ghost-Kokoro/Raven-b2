package net.bplaced.azoq.module.modules.combat;

import net.bplaced.azoq.Client;
import net.bplaced.azoq.event.eventapi.EventTarget;
import net.bplaced.azoq.event.events.EventRunTick;
import net.bplaced.azoq.event.events.EventUpdate;
import net.bplaced.azoq.gui.click.settings.Setting;
import net.bplaced.azoq.module.Module;
import net.bplaced.azoq.module.modules.combat.ModuleAntiBots;
import net.bplaced.azoq.module.modules.combat.ModuleTeams;
import net.bplaced.azoq.utils.MathUtils;
import net.bplaced.azoq.utils.ReflectionUtils;
import net.bplaced.azoq.utils.TimeUtils;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;

@Module.Information(moduleName = "TriggerBot", displayName = "TriggerBot", category = Module.Category.COMBAT)
public class ModuleTriggerBot extends Module {
    private TimeUtils time;
    
    public ModuleTriggerBot() {
        this.time = new TimeUtils();
    }
    
    @Override
    public void setup() {
        super.setup();
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("TriggerBot:Mode", (Module)this, "All Items", new String[] { "All Items", "Hand/Stick/Sword", "Stick/Sword" }));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("TriggerBot:Range", (Module)this, 4.5, 0.0, 10.0, false));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("TriggerBot:Delay", (Module)this, 250.0, 0.0, 1000.0, true));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("TriggerBot:Legit AutoBlock", (Module)this, false));
    }
    
    @EventTarget
    public void onRunTick(final EventRunTick event) {
        this.setDisplayName(this.getModuleName() + " \\2477[" + MathUtils.round(Client.INSTANCE.getSettingsManager().getSettingByName("TriggerBot:Range").getValDouble()) + " | " + (int)Client.INSTANCE.getSettingsManager().getSettingByName("TriggerBot:Delay").getValDouble() + "ms" + "]\247r");
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate event) {
        try {
            final Entity e = ModuleTriggerBot.mc.objectMouseOver.entityHit;
            if (ModuleTriggerBot.mc.currentScreen == null && this.getItem() && ModuleTriggerBot.mc.objectMouseOver.typeOfHit == MovingObjectType.ENTITY && ModuleTriggerBot.mc.thePlayer.getDistanceToEntity(e) <= (float)Client.INSTANCE.getSettingsManager().getSettingByName("TriggerBot:Range").getValDouble() && this.time.hasReached(Client.INSTANCE.getSettingsManager().getSettingByName("TriggerBot:Delay").getValDouble()) && !ModuleAntiBots.isBot(e) && !ModuleTeams.isTeam(e) && this.legitAutoBlock()) {
                ReflectionUtils.clickMouse();
                this.time.reset();
            }
        }
        catch (Exception ex) {}
    }
    
    private boolean getItem() {
        try {
            return Client.INSTANCE.getSettingsManager().getSettingByName("TriggerBot:Mode").getValString().equalsIgnoreCase("All Items") || (Client.INSTANCE.getSettingsManager().getSettingByName("TriggerBot:Mode").getValString().equalsIgnoreCase("Hand/Stick/Sword") && (ModuleTriggerBot.mc.thePlayer.getCurrentEquippedItem() == null || Item.getIdFromItem(ModuleTriggerBot.mc.thePlayer.getCurrentEquippedItem().getItem()) == 280 || ModuleTriggerBot.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword)) || (Client.INSTANCE.getSettingsManager().getSettingByName("TriggerBot:Mode").getValString().equalsIgnoreCase("Stick/Sword") && (Item.getIdFromItem(ModuleTriggerBot.mc.thePlayer.getCurrentEquippedItem().getItem()) == 280 || ModuleTriggerBot.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword));
        }
        catch (Exception e) {
            return false;
        }
    }
    
    private boolean legitAutoBlock() {
        return ModuleTriggerBot.mc.thePlayer.moveForward == 0.0f || ModuleTriggerBot.mc.thePlayer.moveStrafing == 0.0f;
    }
}
