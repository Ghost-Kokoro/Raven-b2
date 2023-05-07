package net.bplaced.azoq.gui.click;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import net.bplaced.azoq.Client;
import net.bplaced.azoq.file.files.FileClickGui;
import net.bplaced.azoq.gui.click.Panel;
import net.bplaced.azoq.gui.click.elements.Element;
import net.bplaced.azoq.gui.click.elements.ModuleButton;
import net.bplaced.azoq.gui.click.elements.menu.ElementSlider;
import net.bplaced.azoq.gui.click.settings.SettingsManager;
import net.bplaced.azoq.module.Module;
import net.bplaced.azoq.utils.ColorUtils;
import net.bplaced.azoq.utils.FontUtils;
import net.bplaced.azoq.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class ClickGUI extends GuiScreen {
    public static ArrayList<Panel> panels;
    public static ArrayList<Panel> rpanels;
    private ModuleButton mb;
    public SettingsManager setmgr;
    
    public ClickGUI() {
        this.mb = null;
        this.setmgr = Client.INSTANCE.getSettingsManager();
        FontUtils.setupFontUtils();
        ClickGUI.panels = new ArrayList<Panel>();
        final double pwidth = 80.0;
        final double pheight = 15.0;
        double px = 10.0;
        double py = 10.0;
        boolean extended = false;
        int i = 0;
        for (final Module.Category c : Module.Category.values()) {
            final String title = Character.toUpperCase(c.name().toLowerCase().charAt(0)) + c.name().toLowerCase().substring(1);
            final String[] args = FileClickGui.clickGui.get(i).split(":");
            if (title.equalsIgnoreCase(args[0])) {
                px = Integer.valueOf(args[1]);
                py = Integer.valueOf(args[2]);
                extended = Boolean.valueOf(args[3]);
            }
            ClickGUI.panels.add(new Panel(title, px, py, pwidth, pheight, extended, this) {
                @Override
                public void setup() {
                    for (final Module m : Client.INSTANCE.getModuleManager().getModules()) {
                        if (!m.getCategory().equals(c)) {
                            continue;
                        }
                        this.Elements.add(new ModuleButton(m, this));
                    }
                }
            });
            ++i;
        }
        ClickGUI.rpanels = new ArrayList<Panel>();
        for (final Panel p : ClickGUI.panels) {
            ClickGUI.rpanels.add(p);
        }
        Collections.reverse(ClickGUI.rpanels);
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        for (final Panel p : ClickGUI.panels) {
            p.drawScreen(mouseX, mouseY, partialTicks);
        }
        final ScaledResolution s = new ScaledResolution(this.mc);
        GL11.glPushMatrix();
        GL11.glTranslated((double)s.getScaledWidth(), (double)s.getScaledHeight(), 0.0);
        GL11.glScaled(0.5, 0.5, 0.5);
        FontUtils.drawStringWithShadow("ByNumberPlok", -Minecraft.getMinecraft().fontRendererObj.getStringWidth("ByNumberPlok"), -Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, -15599509);
        FontUtils.drawStringWithShadow("Code fixed by Ghost", -Minecraft.getMinecraft().fontRendererObj.getStringWidth("Code fixed by Ghost"), -Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT - 11, -15599509);
        GL11.glPopMatrix();
        this.mb = null;
    Label_0249:
        for (final Panel p2 : ClickGUI.panels) {
            if (p2 != null && p2.visible && p2.extended && p2.Elements != null && p2.Elements.size() > 0) {
                for (final ModuleButton e : p2.Elements) {
                    if (e.listening) {
                        this.mb = e;
                        break Label_0249;
                    }
                }
            }
        }
        for (final Panel panel : ClickGUI.panels) {
            if (panel.extended && panel.visible && panel.Elements != null) {
                for (final ModuleButton b : panel.Elements) {
                    if (b.extended && b.menuelements != null && !b.menuelements.isEmpty()) {
                        double off = 0.0;
                        final Color temp = ColorUtils.getClickGUIColor().darker();
                        final int outlineColor = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 170).getRGB();
                        for (final Element e2 : b.menuelements) {
                            e2.offset = off;
                            e2.update();
                            if (Client.INSTANCE.getSettingsManager().getSettingByName("ClickGui:Design").getValString().equalsIgnoreCase("New")) {
                                RenderUtils.drawRect(e2.x, e2.y, e2.x + e2.width + 2.0, e2.y + e2.height, outlineColor);
                            }
                            e2.drawScreen(mouseX, mouseY, partialTicks);
                            off += e2.height;
                        }
                    }
                }
            }
        }
        if (this.mb != null) {
            drawRect(0, 0, this.width, this.height, -2012213232);
            GL11.glPushMatrix();
            GL11.glTranslatef((float)(s.getScaledWidth() / 2), (float)(s.getScaledHeight() / 2), 0.0f);
            GL11.glScalef(4.0f, 4.0f, 0.0f);
            FontUtils.drawTotalCenteredStringWithShadow("Listening...", 0.0, -10.0, -1);
            GL11.glScalef(0.5f, 0.5f, 0.0f);
            FontUtils.drawTotalCenteredStringWithShadow("Press 'ENF' to unbind " + this.mb.mod.getModuleName() + ((this.mb.mod.getKeyBind() > -1) ? (" (" + Keyboard.getKeyName(this.mb.mod.getKeyBind()) + ")") : ""), 0.0, 0.0, -1);
            GL11.glPopMatrix();
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.mb != null) {
            return;
        }
        for (final Panel panel : ClickGUI.rpanels) {
            if (panel.extended && panel.visible && panel.Elements != null) {
                for (final ModuleButton b : panel.Elements) {
                    if (b.extended) {
                        for (final Element e : b.menuelements) {
                            if (e.mouseClicked(mouseX, mouseY, mouseButton)) {
                                return;
                            }
                        }
                    }
                }
            }
        }
        for (final Panel p : ClickGUI.rpanels) {
            if (p.mouseClicked(mouseX, mouseY, mouseButton)) {
                return;
            }
        }
        try {
            super.mouseClicked(mouseX, mouseY, mouseButton);
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        if (this.mb != null) {
            return;
        }
        for (final Panel panel : ClickGUI.rpanels) {
            if (panel.extended && panel.visible && panel.Elements != null) {
                for (final ModuleButton b : panel.Elements) {
                    if (b.extended) {
                        for (final Element e : b.menuelements) {
                            e.mouseReleased(mouseX, mouseY, state);
                        }
                    }
                }
            }
        }
        for (final Panel p : ClickGUI.rpanels) {
            p.mouseReleased(mouseX, mouseY, state);
        }
        super.mouseReleased(mouseX, mouseY, state);
    }
    
    protected void keyTyped(final char typedChar, final int keyCode) {
        for (final Panel p : ClickGUI.rpanels) {
            if (p != null && p.visible && p.extended && p.Elements != null && p.Elements.size() > 0) {
                for (final ModuleButton e : p.Elements) {
                    try {
                        if (e.keyTyped(typedChar, keyCode)) {
                            return;
                        }
                        continue;
                    }
                    catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
        try {
            super.keyTyped(typedChar, keyCode);
        }
        catch (IOException e3) {
            e3.printStackTrace();
        }
    }
    
    public void initGui() {
    }
    
    public void onGuiClosed() {
        for (final Panel panel : ClickGUI.rpanels) {
            if (panel.extended && panel.visible && panel.Elements != null) {
                for (final ModuleButton b : panel.Elements) {
                    if (b.extended) {
                        for (final Element e : b.menuelements) {
                            if (e instanceof ElementSlider) {
                                ((ElementSlider)e).dragging = false;
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void closeAllSettings() {
        for (final Panel p : ClickGUI.rpanels) {
            if (p != null && p.visible && p.extended && p.Elements != null && p.Elements.size() > 0) {
                for (final ModuleButton e : p.Elements) {
                    e.extended = false;
                }
            }
        }
    }
}
