package net.bplaced.azoq.module.modules.render;

import net.bplaced.azoq.Client;
import net.bplaced.azoq.gui.click.settings.Setting;
import net.bplaced.azoq.module.Module;
import net.minecraft.client.gui.GuiScreen;

@Module.Information(moduleName = "ClickGui", displayName = "ClickGui", category = Module.Category.RENDER, keyBind = 54)
public class ModuleClickGui extends Module {
	
	@Override
    public void setup() {
        super.setup();
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("ClickGui:Design", (Module)this, "New", new String[] { "JellyLike", "New" }));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("ClickGui:Sound", (Module)this, false));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("ClickGui:GuiRed", (Module)this, 255.0, 0.0, 255.0, true));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("ClickGui:GuiGreen", (Module)this, 0.0, 0.0, 255.0, true));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("ClickGui:GuiBlue", (Module)this, 0.0, 0.0, 255.0, true));
    }
    
	@Override
    public void onEnable() {
        super.onEnable();
        ModuleClickGui.mc.displayGuiScreen((GuiScreen)Client.INSTANCE.getClickGUI());
        this.toggleModule();
    }
}
