package net.bplaced.azoq.module.modules.render;

import java.awt.Color;
import java.util.Iterator;
import net.bplaced.azoq.Client;
import net.bplaced.azoq.event.eventapi.EventTarget;
import net.bplaced.azoq.event.events.EventRenderGameOverlay;
import net.bplaced.azoq.module.Module;
import net.bplaced.azoq.utils.RenderUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;

@Module.Information(moduleName = "Hud", displayName = "Hud", category = Module.Category.RENDER, visible = false, enabled = true)
public class ModuleHud extends Module {
    @EventTarget
    public void onRenderGameOverlay(final EventRenderGameOverlay event) {
        final Color color = new Color((int)Client.INSTANCE.getSettingsManager().getSettingByName("ArrayList:Red").getValDouble(), (int)Client.INSTANCE.getSettingsManager().getSettingByName("ArrayList:Green").getValDouble(), (int)Client.INSTANCE.getSettingsManager().getSettingByName("ArrayList:Blue").getValDouble());
        final int x = 2;
        int y = 20;
        if (Client.INSTANCE.getSettingsManager().getSettingByName("ArrayList:Side").getValString().equalsIgnoreCase("Left")) {
            final FontRenderer fontRendererObj = ModuleHud.mc.fontRendererObj;
            final StringBuilder sb = new StringBuilder();
            final Client instance = Client.INSTANCE;
            final StringBuilder append = sb.append("\2472R\247aa\247bv\2473e\247ln ").append(" ");
            final Client instance2 = Client.INSTANCE;
            fontRendererObj.drawStringWithShadow(append.append("B2").toString(), (float)x, 5.0f, -1);
            for (final Module m : Client.INSTANCE.getModuleManager().getModulesSorted()) {
                if (m.isEnabled() && m.isVisible()) {
                    String moduleName;
                    if (Client.INSTANCE.getSettingsManager().getSettingByName("ArrayList:Info").getValBoolean()) {
                        moduleName = m.getDisplayName();
                    }
                    else {
                        moduleName = m.getModuleName();
                    }
                    if (Client.INSTANCE.getSettingsManager().getSettingByName("ArrayList:Rect").getValBoolean()) {
                        RenderUtils.drawRect(0.0, y - 1, ModuleHud.mc.fontRendererObj.getStringWidth(moduleName) + 4, y + 9, new Color(0, 0, 0, 100).getRGB());
                    }
                    ModuleHud.mc.fontRendererObj.drawStringWithShadow(moduleName, (float)x, (float)y, color.getRGB());
                    y += 10;
                }
            }
        }
        else {
            final ScaledResolution sr = new ScaledResolution(ModuleHud.mc);
            final FontRenderer fontRendererObj2 = ModuleHud.mc.fontRendererObj;
            final StringBuilder sb2 = new StringBuilder();
            final Client instance3 = Client.INSTANCE;
            final StringBuilder append2 = sb2.append("\2472R\247aa\247bv\2473e\247ln ").append(" ");
            final Client instance4 = Client.INSTANCE;
            final String string = append2.append("B2").toString();
            final int getScaledWidth = sr.getScaledWidth();
            final FontRenderer fontRendererObj3 = ModuleHud.mc.fontRendererObj;
            final StringBuilder sb3 = new StringBuilder();
            final Client instance5 = Client.INSTANCE;
            final StringBuilder append3 = sb3.append("\2472R\247aa\247bv\2473e\247ln ").append(" ");
            final Client instance6 = Client.INSTANCE;
            fontRendererObj2.drawStringWithShadow(string, (float)(getScaledWidth - fontRendererObj3.getStringWidth(append3.append("B1").toString()) - x), 5.0f, -1);
            for (final Module i : Client.INSTANCE.getModuleManager().getModulesSorted()) {
                if (i.isEnabled() && i.isVisible()) {
                    String moduleName2;
                    if (Client.INSTANCE.getSettingsManager().getSettingByName("ArrayList:Info").getValBoolean()) {
                        moduleName2 = i.getDisplayName();
                    }
                    else {
                        moduleName2 = i.getModuleName();
                    }
                    if (Client.INSTANCE.getSettingsManager().getSettingByName("ArrayList:Rect").getValBoolean()) {
                        RenderUtils.drawRect(sr.getScaledWidth(), y - 1, sr.getScaledWidth() - ModuleHud.mc.fontRendererObj.getStringWidth(moduleName2) - 4, y + 9, new Color(0, 0, 0, 150).getRGB());
                    }
                    ModuleHud.mc.fontRendererObj.drawStringWithShadow(moduleName2, (float)(sr.getScaledWidth() - ModuleHud.mc.fontRendererObj.getStringWidth(moduleName2) - x), (float)y, color.getRGB());
                    y += 10;
                }
            }
        }
    }
}
