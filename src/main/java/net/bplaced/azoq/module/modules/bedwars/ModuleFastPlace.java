package net.bplaced.azoq.module.modules.bedwars;

import net.bplaced.azoq.Client;
import net.bplaced.azoq.event.eventapi.EventTarget;
import net.bplaced.azoq.event.events.EventRunTick;
import net.bplaced.azoq.event.events.EventUpdate;
import net.bplaced.azoq.gui.click.settings.Setting;
import net.bplaced.azoq.module.Module;
import net.bplaced.azoq.utils.ReflectionUtils;
import net.bplaced.azoq.utils.TimeUtils;
import net.minecraft.item.ItemBlock;

@Module.Information(moduleName = "FastPlace", displayName = "FastPlace", category = Module.Category.BEDWARS)
public class ModuleFastPlace extends Module {
    private TimeUtils time;
    
    public ModuleFastPlace() {
        this.time = new TimeUtils();
    }
    
    @Override
    public void setup() {
        super.setup();
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("FastPlace:Delay", (Module)this, 250.0, 0.0, 500.0, true));
    }
    
    @EventTarget
    public void onRunTick(final EventRunTick event) {
        this.setDisplayName(this.getModuleName() + " \2477[" + (int)Client.INSTANCE.getSettingsManager().getSettingByName("FastPlace:Delay").getValDouble() + "ms" + "]\247r");
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate event) {
        if (ModuleFastPlace.mc.thePlayer.getCurrentEquippedItem() != null && ModuleFastPlace.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock && this.time.hasReached(Client.INSTANCE.getSettingsManager().getSettingByName("FastPlace:Delay").getValDouble())) {
            ReflectionUtils.setRightClickDelayTimer(0);
            this.time.reset();
        }
    }
}
