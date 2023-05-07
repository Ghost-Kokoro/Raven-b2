package net.bplaced.azoq.gui.click.elements;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import net.bplaced.azoq.Client;
import net.bplaced.azoq.gui.click.Panel;
import net.bplaced.azoq.gui.click.elements.menu.ElementCheckBox;
import net.bplaced.azoq.gui.click.elements.menu.ElementComboBox;
import net.bplaced.azoq.gui.click.elements.menu.ElementSlider;
import net.bplaced.azoq.gui.click.settings.Setting;
import net.bplaced.azoq.module.Module;
import net.bplaced.azoq.module.modules.render.ModuleNotifications;
import net.bplaced.azoq.utils.ChatUtils;
import net.bplaced.azoq.utils.ColorUtils;
import net.bplaced.azoq.utils.FontUtils;
import net.bplaced.azoq.utils.RenderUtils;
import net.minecraft.client.Minecraft;

public class ModuleButton {
    public Module mod;
    public ArrayList<Element> menuelements;
    public Panel parent;
    public double x;
    public double y;
    public double width;
    public double height;
    public boolean extended;
    public boolean listening;
    
    public ModuleButton(final Module imod, final Panel pl) {
        this.extended = false;
        this.listening = false;
        this.mod = imod;
        this.height = Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 2;
        this.parent = pl;
        this.menuelements = new ArrayList<Element>();
        if (Client.INSTANCE.getSettingsManager().getSettingsByMod(imod) != null) {
            for (final Setting s : Client.INSTANCE.getSettingsManager().getSettingsByMod(imod)) {
                if (s.isCheck()) {
                    this.menuelements.add((Element)new ElementCheckBox(this, s));
                }
                else if (s.isSlider()) {
                    this.menuelements.add((Element)new ElementSlider(this, s));
                }
                else {
                    if (!s.isCombo()) {
                        continue;
                    }
                    this.menuelements.add((Element)new ElementComboBox(this, s));
                }
            }
        }
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        final Color temp = ColorUtils.getClickGUIColor();
        final int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 150).getRGB();
        int textcolor = -5263441;
        if (this.mod.isEnabled()) {
            RenderUtils.drawRect(this.x - 2.0, this.y, this.x + this.width + 2.0, this.y + this.height + 1.0, color);
            textcolor = -1052689;
        }
        if (this.isHovered(mouseX, mouseY)) {
            RenderUtils.drawRect(this.x - 2.0, this.y, this.x + this.width + 2.0, this.y + this.height + 1.0, 1427181841);
        }
        FontUtils.drawTotalCenteredStringWithShadow(this.mod.getModuleName(), this.x + this.width / 2.0, this.y + 1.0 + this.height / 2.0, textcolor);
    }
    
    public boolean mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (!this.isHovered(mouseX, mouseY)) {
            return false;
        }
        if (mouseButton == 0) {
            this.mod.toggleModule();
            if (Client.INSTANCE.getSettingsManager().getSettingByName("ClickGui:Sound").getValBoolean()) {
                Minecraft.getMinecraft().thePlayer.playSound("random.click", 0.5f, 0.5f);
            }
        }
        else if (mouseButton == 1) {
            if (this.menuelements != null && this.menuelements.size() > 0) {
                final boolean b = !this.extended;
                Client.INSTANCE.getClickGUI().closeAllSettings();
                this.extended = b;
                if (Client.INSTANCE.getSettingsManager().getSettingByName("ClickGui:Sound").getValBoolean()) {
                    if (this.extended) {
                        Minecraft.getMinecraft().thePlayer.playSound("tile.piston.out", 1.0f, 1.0f);
                    }
                    else {
                        Minecraft.getMinecraft().thePlayer.playSound("tile.piston.in", 1.0f, 1.0f);
                    }
                }
            }
        }
        else if (mouseButton == 2) {
            this.listening = true;
        }
        return true;
    }
    
    public boolean keyTyped(final char typedChar, final int keyCode) throws IOException {
        if (this.listening) {
            if (keyCode != 1 && keyCode != 211) {
                if (Client.INSTANCE.getModuleManager().getModule(ModuleNotifications.class).isEnabled() && Client.INSTANCE.getSettingsManager().getSettingByName("ModuleNotifications:KeyBinds").getValBoolean()) {
                    ChatUtils.sendChatMessage("Bound '" + this.mod.getModuleName() + "'" + " to '" + Keyboard.getKeyName(keyCode) + "'");
                }
                this.mod.setKeyBind(keyCode);
            }
            else if (keyCode == 211) {
                if (Client.INSTANCE.getModuleManager().getModule(ModuleNotifications.class).isEnabled() && Client.INSTANCE.getSettingsManager().getSettingByName("ModuleNotifications:KeyBinds").getValBoolean()) {
                    ChatUtils.sendChatMessage("Unbound '" + this.mod.getModuleName() + "'");
                }
                this.mod.setKeyBind(-1);
            }
            this.listening = false;
            return true;
        }
        return false;
    }
    
    public boolean isHovered(final int mouseX, final int mouseY) {
        return mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + this.height;
    }
}
