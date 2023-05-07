package net.bplaced.azoq.module.modules.render;

import java.util.ArrayList;
import net.bplaced.azoq.Client;
import net.bplaced.azoq.gui.click.settings.Setting;
import net.bplaced.azoq.module.Module;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

@Module.Information(moduleName = "XRay", displayName = "XRay", category = Module.Category.RENDER)
public class ModuleXRay extends Module {
    public static ArrayList<Block> xrayBlocks;
    
    @Override
    public void setup() {
        super.setup();
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("XRay:Diamond", (Module)this, true));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("XRay:Emerald", (Module)this, true));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("XRay:Gold", (Module)this, true));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("XRay:Iron", (Module)this, true));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("XRay:Redstone", (Module)this, true));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("XRay:Bed", (Module)this, true));
    }
    
    @Override
    public void onEnable() {
        ModuleXRay.xrayBlocks.clear();
        if (Client.INSTANCE.getSettingsManager().getSettingByName("XRay:Diamond").getValBoolean()) {
            ModuleXRay.xrayBlocks.add(Blocks.diamond_ore);
        }
        if (Client.INSTANCE.getSettingsManager().getSettingByName("XRay:Emerald").getValBoolean()) {
            ModuleXRay.xrayBlocks.add(Blocks.emerald_ore);
        }
        if (Client.INSTANCE.getSettingsManager().getSettingByName("XRay:Gold").getValBoolean()) {
            ModuleXRay.xrayBlocks.add(Blocks.gold_ore);
        }
        if (Client.INSTANCE.getSettingsManager().getSettingByName("XRay:Iron").getValBoolean()) {
            ModuleXRay.xrayBlocks.add(Blocks.iron_ore);
        }
        if (Client.INSTANCE.getSettingsManager().getSettingByName("XRay:Redstone").getValBoolean()) {
            ModuleXRay.xrayBlocks.add(Blocks.redstone_ore);
        }
        if (Client.INSTANCE.getSettingsManager().getSettingByName("XRay:Bed").getValBoolean()) {
            ModuleXRay.xrayBlocks.add(Blocks.bed);
        }
        ModuleXRay.mc.gameSettings.ambientOcclusion = 0;
        ModuleXRay.mc.renderGlobal.loadRenderers();
    }
    
    @Override
    public void onDisable() {
        ModuleXRay.mc.gameSettings.ambientOcclusion = 1;
        ModuleXRay.mc.renderGlobal.loadRenderers();
    }
    
    static {
        ModuleXRay.xrayBlocks = new ArrayList<Block>();
    }
}
